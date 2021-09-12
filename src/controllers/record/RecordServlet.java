package controllers.record;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Study;
import models.User;
import utils.DBUtil;

/**
 * Servlet implementation class RecordServlet
 */
@WebServlet("/record")
public class RecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecordServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManager em = DBUtil.createEntityManager();

		Study s = new Study();

		// 記録の対象となるユーザーをセット
		s.setUser((User)request.getSession().getAttribute("login_user"));

		// 選択された学習時間をセット
		String stu_hour = request.getParameter("stu_hour");  // パラメータは文字でしか取得できないから、String型で取得
		s.setStudy_hour(Integer.parseInt(stu_hour));  // int型のStudy_hourにセットできるように、パラメータの中身のint型の要素を取りだす

		em.getTransaction().begin();
		em.persist(s);
		em.getTransaction().commit();
		em.close();
		request.getSession().setAttribute("flush", "記録が完了しました。");

		response.sendRedirect(request.getContextPath() + "/index.html" );
	}

}
