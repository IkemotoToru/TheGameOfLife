package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.playerDAO;
import model.UserRegistrationBeans;

/**
 * Servlet implementation class UserRegistration
 */
@WebServlet("/UserRegistration")
public class UserRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserRegistration.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//リクエストパラメータ取得
		request.setCharacterEncoding("UTF-8");
		String playerName = request.getParameter("playerName");
		String strPlayerPass = request.getParameter("playerPass");

		playerDAO dao = new playerDAO();
		UserRegistrationBeans urb = null;
		try {
			urb = dao.findPlayer(playerName);
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		boolean isCreate = (urb != null && playerName.equals(urb.getName()));
		HttpSession session = request.getSession();
		session.setAttribute("isCreate", isCreate);

		if (!isCreate) {
			if (playerName.length() != 0) {

				if (strPlayerPass.length() == 4) {
					try {
						int playerPass = Integer.parseInt(strPlayerPass);
						int addCnt = dao.playerCreate(playerName, playerPass);
						request.setAttribute("playserName", playerName);
						request.setAttribute("mes", "登録が完了しました。");
					} catch (Exception e) {
						request.setAttribute("mes", "数字を入力してください。");
					}
				} else {
					request.setAttribute("mes", "4桁の数字を入力してください。");
				}
			} else {
				request.setAttribute("mes", "nameが未入力です。");
			}
		} else {
			request.setAttribute("mes", "その名前は既に使われています。");
		}
		request.getRequestDispatcher("WEB-INF/jsp/UserRegistration.jsp").forward(request, response);
	}
}
