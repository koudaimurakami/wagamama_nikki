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
import models.Calendars;
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



		cls = logic.createCalendars();
		// 学習時間を記録する（日にちまで表示）

		// パターン①
		Calendar cl = Calendar.getInstance();
		request.setAttribute("daily", cl);

		// もう一つのパターン  パターン②
		request.setAttribute("cls_r", cls);




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
			cls = logic.createCalendars(year,month);
			dates = logic.generateDays(year, month);
		}else {
			//クエリパラメータが来ていないときは実行日時のカレンダーを生成する。
			cls = logic.createCalendars();

			dates = logic.generateDays(year, month);
		}


		// 今月を指定
		// Date の二次元配列を生成する

		int weekStart = dates.get(0).get(Calendar.DAY_OF_WEEK)-1;
		int monthEnd = dates.get(0).getActualMaximum(Calendar.DATE);








		// 日曜日から開始するため、先頭にnullを挿入
		for (int i = 0; i < weekStart; i++) {
			dates.add(i, null);
		}

		// dates.size() に応じて残りのカレンダーの null を埋める
		int calendarRows = (weekStart + monthEnd) / 7 + 1;
		for (int i = dates.size(); i < 7 * calendarRows; i++) {    // ←月の日数分より大きく、残りの空白を満たしていない範囲
			dates.add(i, null);
		}

		/*
		// 前月を表示
		ArrayList<Calendar> last_month = logic.generateDays(year-1,month-1);

		for (int i = 0; i < weekStart; i++) {
			last_month.add(i, null);
		}

		for (int j = last_month.size(); j < 7 * calendarRows; j++) {    // ←月の日数分より大きく、残りの空白を満たしていない範囲
				last_month.add(j, null);
		}
		*/

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



		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/index.jsp");
		rd.forward(request, response);
	}

}
