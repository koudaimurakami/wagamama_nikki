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

		String s_year = request.getParameter("year");
		String s_month = request.getParameter("month");

		int year;
		int month;
		if (s_year == null || s_month == null) {
		    Calendar calend = Calendar.getInstance();
		    year = calend.get(Calendar.YEAR);
		    month = calend.get(Calendar.MONTH)+1;
		} else {
		    year = Integer.parseInt(s_year);
		    month = Integer.parseInt(s_month);
		}

		/*
		Calendars cls = null;

		if(s_year != null && s_month != null) {


			if(month == 0) {
				month = 12;
				year--;
			}
			if(month == 13) {
				month = 1;
				year++;
			}
			//年と月のクエリパラメーターが来ている場合にはその年月でカレンダーを生成する
			cls = logic.createCalendars(year,month);
		}else {
			//クエリパラメータが来ていないときは実行日時のカレンダーを生成する。
			cls = logic.createCalendars();
		}
		//リクエストスコープに格納
		request.setAttribute("cls", cls);
		*/

		//cls = logic.createCalendars();

		// ○○年○月○日の学習時間を記録するのリンクのところの、日付表示で使用する
		Calendar cl = Calendar.getInstance();
		request.setAttribute("daily", cl);

		// カレンダーの列数を求める際に使用する変数を用意しておく
		//Calendar calen = Calendar.getInstance();
		//int monthEnd = calen.getActualMaximum(Calendar.DAY_OF_MONTH);
		//calen.set(Calendar.DATE, monthEnd);
		//int after = 7-calen.get(Calendar.DAY_OF_WEEK);

		ArrayList<Calendar> dates;

		if(s_year != null && s_month != null) {

			if(month == 0) {
				month = 12;
				year--;
			}
			if(month == 13) {
				month = 1;
				year++;
			}
			//年と月のクエリパラメーターが来ている場合にはその年月でカレンダーを生成する
			//cls = logic.createCalendars(year,month);
			dates = logic.generateDays(year, month);
		}else {
			//クエリパラメータが来ていないときは実行日時のカレンダーを生成する。
			//cls = logic.createCalendars();

			dates = logic.generateDays(year, month);
		}


		// 今月を指定
		// Date の二次元配列を生成する

		/*
		//その月の1日が何曜日かを調べる為に日付を1日にする
		cal.set(Calendar.DATE, 1);                         // → この時点から、その月の一日のCalendarsを操作することになる
		//カレンダーの最初の空白の数
		int before = cal.get(Calendar.DAY_OF_WEEK)-1;
		//カレンダーの日付の数
		int daysCount = cal.getActualMaximum(Calendar.DAY_OF_MONTH);  // ここまで、その月の一日の操作


		//その月の最後の日が何曜日かを調べるために日付を最終日にする
		cal.set(Calendar.DATE, daysCount);                           // → この時点から、その月の最終日のCalendarsを操作することになる
		//最後の日後の空白の数
		int after = 7-cal.get(Calendar.DAY_OF_WEEK);
		//すべての要素数
		int total = before+daysCount+after;
		//その要素数を幅7個の配列に入れていった場合何行になるか
		int rows = total/7;
		*/

		int weekStart = dates.get(0).get(Calendar.DAY_OF_WEEK)-1;  // int before に同じ
		int monthEnd = dates.get(0).getActualMaximum(Calendar.DATE);  // int daysCount に同じ
		//int total = weekStart + monthEnd + after;


		// 日曜日から開始するため、先頭にnullを挿入
		for (int i = 0; i < weekStart; i++) {
			dates.add(i, null);
		}

		// dates.size() に応じて残りのカレンダーの null を埋める
		int calendarRows = (weekStart + monthEnd) / 7 ;  // ← 本来 + 1がある
		for (int i = dates.size(); i < 7 * calendarRows; i++) {    // ←月の日数分より大きく、残りの空白を満たしていない範囲
			dates.add(i, null);
		}

		//for (int i = dates.size(); i < 7 * calendarRows; i++) {    // ←月の日数分より大きく、残りの空白を満たしていない範囲
			//dates.add(i, null);
		//}



		User login_user = (User) request.getSession().getAttribute("login_user");

		Calendar dis_month_1 = Calendar.getInstance();
		dis_month_1.clear();
		dis_month_1.set(year, month-1, 1);

		System.out.println(dis_month_1 + "今月の一日");

		int dis_month_count = dis_month_1.getActualMaximum(Calendar.DAY_OF_MONTH);
		System.out.println(dis_month_count + "今月の日数");

		Calendar dis_month_last = Calendar.getInstance();
		dis_month_last.clear();
		dis_month_last.set(year, month-1, dis_month_count);

		System.out.println(dis_month_last + "今月の最終日");

		List<Study> study_date = em.createNamedQuery("StudyLog", Study.class)
									.setParameter("user", login_user)
									.setParameter("this_month_1", dis_month_1, TemporalType.DATE)
									.setParameter("this_month_last", dis_month_last, TemporalType.DATE)
									.getResultList();

		request.setAttribute("study_date", study_date);



		Calendar today = dates.get(weekStart);
		request.setAttribute("today",today);

		request.setAttribute("dates", dates);



		if (login_user != null) {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/index.jsp");
			rd.forward(request, response);
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login/login.jsp");    // ログイン状態が切れていたら、ログイン画面に戻る
            rd.forward(request, response);
		}

	}

}
