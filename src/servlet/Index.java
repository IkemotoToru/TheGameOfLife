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
import model.NowPlayer;
import model.UserRegistrationBeans;

@WebServlet("/Index")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//ログアウト作業


		//セッションスコープをすべて破棄する。
		HttpSession session = request.getSession();
		session.invalidate();

		String msg = "ログアウトしました。";

		request.setAttribute("msg", msg);

		// 最初の画面にフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		//リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String pass = request.getParameter("pass");

		HttpSession session = request.getSession();


		playerDAO playerDAO = new playerDAO();
		List<UserRegistrationBeans> urbList = playerDAO.all();

		Boolean count = true;
		String msg = "";

		if(urbList.size() != 0) {

			if(name.length() != 0) {

				if(pass.length() != 0) {

					for(int i = 0 ; i <= urbList.size() - 1 ; i++) {

						UserRegistrationBeans urb = urbList.get(i);
						String urbName = urb.getName();
						int urbPass = urb.getPass();
						String urbPassStr = "" + urbPass;

						if(name.equals(urbName) && pass.equals(urbPassStr)) {
							count = true;

							NowPlayer nowPlayer = new NowPlayer(name);

							session.setAttribute("name" , name);
							session.setAttribute("pass" , pass);
							session.setAttribute("nowPlayer" , nowPlayer);

							msg = "ログインに成功しました。";

							i = urbList.size();

						}else {
							count = false;
							msg = "ログインに失敗しました。";
						}
					}
				}else {
					count = false;
					msg += "パスワードを入力してください。";
				}
			}else{

				count = false;
				msg += "名前を入力してください。";
			}
		}else {
			count = false;
			msg = "ログインに失敗しました。";
		}

	session.setAttribute("count" , count);
	session.setAttribute("msg",msg);

	// 最初の画面にフォワード
	RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
	dispatcher.forward(request,response);
	}
}
