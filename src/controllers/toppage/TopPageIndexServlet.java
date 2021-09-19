package controllers.toppage;

import java.io.IOException;
import java.text.SimpleDateFormat;
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


		Calendars cls = null;

		if(s_year != null && s_month != null) {
			int year = Integer.parseInt(s_year);
			int month = Integer.parseInt(s_month);
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

		// SimpleDateFormatクラスを使用

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		request.setAttribute("daily", sdf.format(cl.getTime()));

		// もう一つのパターン  パターン②
		request.setAttribute("cls_r", cls);




		// 今月を指定
		// Date の二次元配列を生成する
		ArrayList<Calendar> dates = logic.generateDays();
		int weekStart = dates.get(0).get(Calendar.DAY_OF_WEEK)-1;
		int monthEnd = dates.get(0).getActualMaximum(Calendar.DATE);


		/*
		// ここからサンプルで、2021/1月を指定
		// Date の二次元配列を生成する
		ArrayList<Calendar> dates = logic.generateDays(2021,1);
		int weekStart = dates.get(0).get(Calendar.DAY_OF_WEEK)-1;
		*/



		// 日曜日から開始するため、先頭にnullを挿入
		for (int i = 0; i < weekStart; i++) {
			dates.add(i, null);
		}

		// dates.size() に応じて残りのカレンダーの null を埋める
		int calendarRows = (weekStart + monthEnd) / 7 + 1;
		for (int i = dates.size(); i < 7 * calendarRows; i++) {    // ←月の日数分より大きく、残りの空白を満たしていない範囲
			dates.add(i, null);
		}

		User login_user = (User) request.getSession().getAttribute("login_user");

		Calendar dis_month_1 = Calendar.getInstance();
		dis_month_1.set(Calendar.DATE, 1);
		System.out.println(dis_month_1 + "今月の一日");

		int dis_month_count = dis_month_1.getActualMaximum(Calendar.DAY_OF_MONTH);
		System.out.println(dis_month_count + "今月の日数");

		Calendar dis_month_last = Calendar.getInstance();
		dis_month_last.set(Calendar.DATE, dis_month_count);
		System.out.println(dis_month_last + "今月の最終日");

		List<Study> study_date = em.createNamedQuery("StudyLog", Study.class)
									.setParameter("user", login_user)
									.setParameter("this_month_1", dis_month_1, TemporalType.DATE)
									.setParameter("this_month_last", dis_month_last, TemporalType.DATE)
									.getResultList();

		request.setAttribute("study_date", study_date);



		request.setAttribute("dates", dates);



		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/index.jsp");
		rd.forward(request, response);
	}

}
