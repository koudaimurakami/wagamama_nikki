package controllers.toppage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.CalendarLogic;
import models.Study;
import models.User;
import utils.DBUtil;

/**
 * Servlet implementation class TopPageIndexServlet
 */
@WebServlet("/index.html")
public class TopPageIndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TopPageIndexServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		EntityManager em = DBUtil.createEntityManager();
		CalendarLogic logic = new CalendarLogic();

		// リクエストパラメータを取得する
		String str_year = request.getParameter("year");
		String str_month = request.getParameter("month");

		int year;
		int month;
		if (str_year == null || str_month == null) {
		    Calendar calend = Calendar.getInstance();
		    year = calend.get(Calendar.YEAR);
		    month = calend.get(Calendar.MONTH)+1;
		} else {
		    year = Integer.parseInt(str_year);
		    month = Integer.parseInt(str_month);
		}




		/*
		 *  日付のデータを保持する ArrayListを作成
		 */
		ArrayList<Calendar> date_table;

		if(str_year != null && str_month != null) {

			if(month == 0) {
				month = 12;
				year--;
			}
			if(month == 13) {
				month = 1;
				year++;
			}
		}
		// 年と月のパラメータが来ている場合には、その年月のカレンダーを生成する
		date_table = logic.generateDays(year, month);


		int weekStart = date_table.get(0).get(Calendar.DAY_OF_WEEK)-1;  // その月の一日が何曜日か
		int monthDaysCount = date_table.get(0).getActualMaximum(Calendar.DATE);  // その月の日数を取得
		//int total = weekStart + monthEnd + after;


		// 日曜日から開始するため、先頭にnullを挿入
		for (int i = 0; i < weekStart; i++) {
			date_table.add(i, null);
		}

		// date_table.size() に応じて残りのカレンダーの null を埋める
		int calendarRows = (int) Math.ceil((double)(weekStart + monthDaysCount) / 7) ;
		System.out.println(calendarRows + "カレンダーローズ");
		int size = date_table.size();
		for (int i = size; i < 7 * calendarRows; i++) {    // ←月の日数分より大きく、残りの空白を満たしていない範囲
			date_table.add(null);
		}

		request.setAttribute("date_table", date_table);




		/*
		 *  ↓ Studyクラスの select文のリストを作成  ↓
		 */

		User login_user = (User) request.getSession().getAttribute("login_user");


		// 今月の一日を保持する変数を作成
		Calendar dis_month_1 = Calendar.getInstance();
		dis_month_1.clear();
		dis_month_1.set(year, month-1, 1);

		// 今月の日数を保持する変数を作成
		int dis_month_count = dis_month_1.getActualMaximum(Calendar.DAY_OF_MONTH);

		// 今月の最終日を保持する変数を作成
		Calendar dis_month_last = Calendar.getInstance();
		dis_month_last.clear();
		dis_month_last.set(year, month-1, dis_month_count);

		List<Study> study_date = em.createNamedQuery("StudyLog", Study.class)
									.setParameter("user", login_user)
									.setParameter("this_month_1", dis_month_1, TemporalType.DATE)
									.setParameter("this_month_last", dis_month_last, TemporalType.DATE)
									.getResultList();

		request.setAttribute("study_date", study_date);




		/*
		 *  「○年○月のカレンダー」という表記を表示するための変数を作成
		 */
		Calendar toppageCalendar = date_table.get(weekStart);
		request.setAttribute("toppageCalendar",toppageCalendar);



		/*
		 *  ○○年○月○日の学習時間を記録するのリンクのところの、日付表示で使用する
		 */
		Calendar today = Calendar.getInstance();
		request.setAttribute("today", today);





		// ビューとなるjspを指定して表示する
		if (login_user != null) {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/index.jsp");
			rd.forward(request, response);
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login/login.jsp");    // ログイン状態が切れていたら、ログイン画面に戻る
            rd.forward(request, response);
		}

	}

}
