package controllers.toppage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.CalendarLogic;
import models.Calendars;

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

		// 今月の日数分より後で、42に満たない分も null を追加
		// （カレンダーは最大で、6週表示されることがあるから）
		for (int i = dates.size(); i < 7 * 6; i++) {    // ←月の日数分より大きく、残りの空白を満たしていない範囲
			dates.add(i, null);
		}


		request.setAttribute("dates", dates);



		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/index.jsp");
		rd.forward(request, response);
	}

}
