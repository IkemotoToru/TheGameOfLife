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
import javax.servlet.http.HttpSession;

import dao.textlogDAO;
import model.NowPlayer;
import model.Textlog;

@WebServlet("/Inquiries")
public class Inquiries extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String count = request.getParameter("count");

		HttpSession session = request.getSession();
		NowPlayer nowPlayer = (NowPlayer) session.getAttribute("nowPlayer");

		String url = null;
		List<Textlog> logList = null;

		textlogDAO logDAO = new textlogDAO();

		if(nowPlayer == null) {

			url = "/index.jsp";
			session.setAttribute("msg" , "ログインしてください。");

		}else {

			String msg = null;

			String radio = request.getParameter("hobby");
			String text = request.getParameter("text");
			String mail = request.getParameter("email");

			if(count != null) {
				if(radio != null) {
					if(text.length() != 0) {

						logDAO.add(nowPlayer.getName() , radio , text , mail);
					}else {
						msg = "必須項目が未入力です。";
					}
				}else {
					msg = "必須項目が未入力です。";
				}
			}

			logList = new ArrayList<Textlog>();
			logList = logDAO.all();

			request.setAttribute("msg" , msg);
			request.setAttribute("logList" , logList);

			url = "/WEB-INF/jsp/inquiries.jsp";
		}


		//urlによって変わるフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request,response);
	}
}
