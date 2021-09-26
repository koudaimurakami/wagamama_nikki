package mypage;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.User;
import utils.DBUtil;

/**
 * Servlet implementation class MyPageServlet
 */
@WebServlet("/mypage")
public class MyPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyPageServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManager em = DBUtil.createEntityManager();

		User login_user = (User) request.getSession().getAttribute("login_user");

		Object total_study_hour = em.createNamedQuery("sumStudyHour")
										.setParameter("user", login_user)
										.getSingleResult();

		em.close();


		request.setAttribute("user", login_user);
		request.setAttribute("tsh", total_study_hour);

		// ビューとなるjspを指定して表示する
		if (login_user != null) {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/myPage/index.jsp");
			rd.forward(request, response);
		} else {
			 RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login/login.jsp");    // ログイン状態が切れていたら、ログイン画面に戻る
	            rd.forward(request, response);
		}

	}

}
