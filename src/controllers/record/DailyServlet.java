package controllers.record;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Study;
import models.User;

/**
 * Servlet implementation class DailyServlet
 */
@WebServlet("/daily")
public class DailyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DailyServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("_token", request.getSession().getId());

		Study s = new Study();

		// ログインしているユーザーの情報を予め格納しておく
		User login_user = (User) request.getSession().getAttribute("login_user");
		s.setUser(login_user);

		request.setAttribute("study", s);

		if (login_user != null) {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/record/record.jsp");
			rd.forward(request, response);
		} else {
			 RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login/login.jsp");    // ログイン状態が切れていたらログイン画面に戻る
	         rd.forward(request, response);
		}



	}

}
