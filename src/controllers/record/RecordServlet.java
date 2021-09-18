package controllers.record;

import java.io.IOException;
import java.sql.Date;

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

		// 学習した日付を studyクラスの study_date にセット
		Date study_date = new Date(System.currentTimeMillis());
        String sd_str = request.getParameter("study_date");
        if(sd_str != null && !sd_str.equals("")) {
            study_date = Date.valueOf(request.getParameter("study_date"));
        }
        s.setStudy_date(study_date);


		// 選択された学習時間をセット
		String stu_hour = request.getParameter("stu_hour");  // パラメータは文字でしか取得できないから、String型で取得
		s.setStudy_hour(Integer.parseInt(stu_hour));  // int型のStudy_hourにセットできるように、パラメータの中身のint型の要素を取りだす

		// 学習時間の印を変数に入れる
		String hoshi = "☆";
		String nijuu_maru = "◎";
		String maru = "○";
		String onpu = "♪";


		if ((stu_hour.equals("10")) || (stu_hour.equals("9")) || (stu_hour.equals("8"))) {
			request.getSession().setAttribute("mark", hoshi);
		} else if ((stu_hour.equals("7")) || (stu_hour.equals("6")) || (stu_hour.equals("5")) || (stu_hour.equals("4"))) {
			request.getSession().setAttribute("mark", nijuu_maru);
		} else if ((stu_hour.equals("3")) || (stu_hour.equals("2")) || (stu_hour.equals("1"))) {
			request.getSession().setAttribute("mark", maru);
		} else if (stu_hour.equals("0")) {
			request.getSession().setAttribute("mark", onpu);
		}

		// 学習時間を記録した日付をセット
		//Calendar real_time = Calendar.getInstance();
		//SimpleDateFormat now = new SimpleDateFormat("yyyy/MM/dd");
		//String study_date = now.format(real_time.getTime());

		em.getTransaction().begin();
		em.persist(s);
		em.getTransaction().commit();
		em.close();
		request.getSession().setAttribute("flush", "記録が完了しました。");

		// 学習時間を記録した日付を、TopPageの index.jspに渡す
		request.getSession().setAttribute("record_day", study_date);

		response.sendRedirect(request.getContextPath() + "/index.html" );
	}

}
