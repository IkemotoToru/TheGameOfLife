package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.playerDAO;
import model.RankingBeans;

/**
 * Servlet ランキング
 */
@WebServlet("/Ranking")
public class Ranking extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

//		HttpSession session = request.getSession();
//		List<RankingBeans> pRanking =  (List<RankingBeans>) session.getAttribute("pRanking");
		List<RankingBeans> pRanking =  (List<RankingBeans>) request.getAttribute("pRanking");

		String url = null;

		if(pRanking == null) {
			url = "/index.jsp";
		}else {
		url = "/WEB-INF/jsp/GoalScene.jsp";
		}

		playerDAO playerDAO = new playerDAO();
		pRanking = new ArrayList<RankingBeans>();
		pRanking = playerDAO.ranking();

//		session.setAttribute("pRanking", pRanking);
		request.setAttribute("pRanking", pRanking);

		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

}
