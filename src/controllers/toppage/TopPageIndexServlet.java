package controllers.toppage;

import java.io.IOException;
import java.text.SimpleDateFormat;
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

		String s_year = request.getParameter("year");
		String s_month = request.getParameter("month");

		CalendarLogic logic = new CalendarLogic();

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


		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/index.jsp");
		rd.forward(request, response);
	}

}
