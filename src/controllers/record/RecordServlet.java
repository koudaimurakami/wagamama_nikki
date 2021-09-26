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

		// Studyのインスタンスを生成
		Study s = new Study();

		/*
		 * 記録の対象となるユーザーをセット
		 * sendRedirect の時の判定に使用したいので、ログインユーザーの変数を作成しておく
		 */
		User login_user = ((User)request.getSession().getAttribute("login_user"));
		s.setUser(login_user);



		/*
		 * 学習した日付を studyクラスの study_date にセット
		 */
		Date study_date = new Date(System.currentTimeMillis());
        String sd_str = request.getParameter("study_date");
        if(sd_str != null && !sd_str.equals("")) {
            study_date = Date.valueOf(request.getParameter("study_date"));
        }
        s.setStudy_date(study_date);



        /*
         * 選択された学習時間をセット
         */
		String stu_hour = request.getParameter("stu_hour");  // パラメータは文字でしか取得できないから、String型で取得
		s.setStudy_hour(Integer.parseInt(stu_hour));  // int型のStudy_hourにセットできるように、パラメータの中身のint型の要素を取りだす



		em.getTransaction().begin();
		em.persist(s);                   // データベースに保存
		em.getTransaction().commit();    // データの新規登録を確定させる
		em.close();
		request.getSession().setAttribute("flush", "記録が完了しました。");

		// 学習時間を記録した日付を、TopPageの index.jspに渡す
		request.getSession().setAttribute("record_day", study_date);

		if (login_user != null) {
			response.sendRedirect(request.getContextPath() + "/index.html" );
		} else {
			response.sendRedirect(request.getContextPath() + "/login");    // ログイン状態が切れていたら、ログイン画面に戻る
		}

	}

}
