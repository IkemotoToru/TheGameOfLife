package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.playerDAO;
import model.GameLogic;
import model.Masu;
import model.NowPlayer;
import model.RankingBeans;


@WebServlet("/Game")
public class Game extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String game = request.getParameter("game");

		String url = "";

		switch(game) {
		case "スタート":

			//セッションスコープから取得
			HttpSession session = request.getSession();

			//マスの自動作成
			GameLogic gameLogic = new GameLogic();
			List<Masu> gameList = gameLogic.masuCreate();

			//プレーしている人の情報
			NowPlayer nowPlayer = (NowPlayer) session.getAttribute("nowPlayer");

			nowPlayer.setJob("無職");
			nowPlayer.setMoney(50000);
			nowPlayer.setSalary(0);
			nowPlayer.setAdvanceNumber(0);
			nowPlayer.setTurnCount(0);
			nowPlayer.setRandom(0);

			//セッションスコープに保存
			session.setAttribute("gameList" , gameList);
			session.setAttribute("nowPlayer" , nowPlayer);

			url = "/WEB-INF/jsp/game.jsp";

			break;
		case "ルール":

			url = "/WEB-INF/jsp/RuleScene.jsp";
			break;
		}

		//ゲーム画面かルール説明画面にフォワード
		RequestDispatcher dispatcher =request.getRequestDispatcher(url);
		dispatcher.forward(request, response);

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//セッションスコープから取得
		HttpSession session = request.getSession();
		List<Masu> gameList = (List<Masu>)session.getAttribute("gameList");
		NowPlayer nowPlayer = (NowPlayer)session.getAttribute("nowPlayer");

		GameLogic gameLogic = new GameLogic();
		String image = gameLogic.advance(gameList , nowPlayer);

		String url;
		if(nowPlayer.getAdvanceNumber() == gameList.size()) {
			//ゴールした場合動作
			url = "/WEB-INF/jsp/GoalScene.jsp";
			playerDAO playerDAO = new playerDAO();
			boolean update = playerDAO.bestScore(nowPlayer);
			List<RankingBeans> pRanking = playerDAO.ranking();
			request.setAttribute("pRanking", pRanking);
			String msg = "";
			if(update) {
				msg = "自己新記録!!";
			}else {
				msg = "自己新記録ならず！！";
			}
			session.setAttribute("msg" , msg);

		}else {
			//ゴールしてない場合動作
			url = "/WEB-INF/jsp/game.jsp";
		}

		//リクエストスコープに保存
		request.setAttribute("image" , image);

		//セッションスコープに保存
		session.setAttribute("nowPlayer" , nowPlayer);

		// ユーザ登録画面にフォワード
		RequestDispatcher dispatcher =request.getRequestDispatcher(url);
		dispatcher.forward(request, response);

	}

}
