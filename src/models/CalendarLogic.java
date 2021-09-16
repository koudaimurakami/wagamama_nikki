package models;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarLogic {


	//カレンダーインスタンスを生成するメソッド(int...は可変長引数)
		public Calendars createCalendars(int... args) {
			//Calendarsクラスのインスタンス生成
			Calendars cls = new Calendars();
			//現在日時でカレンダーインスタンス生成
			Calendar cal = Calendar.getInstance();
			//2つの引数が来ていたら
			if(args.length==2) {
				//最初の引数で年を設定
				cal.set(Calendar.YEAR, args[0]);
				//次の引数で月を設定
				cal.set(Calendar.MONTH, args[1]-1);
			}
			//clsに年を設定
			cls.setYear(cal.get(Calendar.YEAR));  // → この時点から、今年のCalendarsを操作することになる
			// Calendarsに月の設定
			cls.setMonth(cal.get(Calendar.MONTH)+1); // → この時点から、今月のCalendarsを操作することになる


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

			//その行数で2次元配列を生成
			String[][] date = new String[rows][7];

			// 本日の日付の欄に"●"の印を付けるため、今のこの瞬間の日付情報をもつもう一つのインスタンス作成しておく
		    Calendar now = Calendar.getInstance();
			for(int i = 0; i < rows; i++) {
				for(int j = 0; j < 7; j++) {
					if(i == 0 && j < before || i == rows-1 && j >= (7-after)) {  // 縦の1行目かつ横の日数が前月の日より小さいとき、または、縦の最後の行かつ横の日数が(7日-翌月の日)より大きいとき
						//カレンダーの前後に入る空白の部分は空文字
						date[i][j]="";  // 空文字を入れる
					}else {
						//カウンター変数と実際の日付の変換
						int date_data = i * 7 + j + 1 - before;
						//配列に日付を入れる
						date[i][j]=String.valueOf(date_data);
						int weeks = now.get(Calendar.WEEK_OF_MONTH) - 1;  // その月の何週目か（配列の縦の何行目か）
						int the_day = now.get(Calendar.DAY_OF_WEEK) - 1;  // 何曜日か（配列の横の何個目か）


						/*
						// これらの変数の中身をコンソールで確認
						System.out.println(now.get(Calendar.DATE) + "あ");
						System.out.println(date_today + "い");
						System.out.println(now.get(Calendar.MONTH)+1 + "う");
						System.out.println(cls.getMonth() + "え");
						System.out.println(now.get(Calendar.YEAR) + "お");
						System.out.println(cls.getYear() + "か");
						System.out.println(rows + "き");
						System.out.println(weeks + "く");
						System.out.println(the_day + "け");
						*/

						//今作業している Calendars が今この瞬間のリアルタイムと同じだったら、今月のカレンダーの今日の日付の先頭に ● を付ける
						if(now.get(Calendar.MONTH)+1 == cls.getMonth()  && now.get(Calendar.YEAR) == cls.getYear() && 0 <= i && i < rows && 0 <= j && j < 7 && i == weeks && j == the_day ) {
							date[weeks][the_day] = "●" + date[i][j];
						} else {
							System.out.println("指定されたインデックスの値が不正です。weeks = " + weeks + "the_day = " + the_day );
						}
					}
				}
			}
			//作成した2次元配列を Calendarsにセットする。
			cls.setDate(date);  // data > date
			return cls;
		}



		/**
		当月の1日から末日までのArrayListを生成する
		@return ArrayList<Calendar>
		*/
		public ArrayList<Calendar> generateDays() {

			//空のArrayListを生成
			ArrayList<Calendar> cl = new ArrayList<Calendar>();
			Calendar cal =Calendar.getInstance();
			return this.generateCalendars(cl, cal);
		}


		// 月の日数分をArrayListに追加していく似たような処理は、
		// generateDays() と、generateDays(int year, int month) とで、共通化する
		private ArrayList<Calendar> generateCalendars(ArrayList<Calendar> cl, Calendar cal) {
			// 月の末日
			int lastDay = cal.getActualMaximum(Calendar.DATE);

			// その月の日数分だけCalendarのインスタンスを作って、clに追加していく
			for (int i = 0; i < lastDay; i++) {
				Calendar newCal = (Calendar)cal.clone();
				newCal.set(Calendar.DATE, i+1);
				cl.add(newCal);
			}
			return cl;
		}


		/**
		指定された年月の１日から末日までのArrayListを生成する
		@parem int year
		@int month
		@ArrayList<Calendar>
		*/
		public ArrayList<Calendar> generateDays(int year, int month) {

			// 空のArrayListを生成
			ArrayList<Calendar> cl = new ArrayList<Calendar>();
			Calendar cal = Calendar.getInstance();
			cal.clear();
			cal.set(year, month-1, 1);

			return this.generateCalendars(cl, cal);
		}
}
