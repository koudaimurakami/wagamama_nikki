<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="models.Study, java.util.ArrayList, java.util.Calendar, java.text.SimpleDateFormat, java.util.Date, java.util.List" %>
<%
@SuppressWarnings("unchecked")
ArrayList<Calendar> date_table = (ArrayList<Calendar>)request.getAttribute("date_table");
%>
<%
@SuppressWarnings("unchecked")
List<Study> study_date =  (List<Study>)request.getAttribute("study_date"); %>

<% Date record_day = (Date)request.getSession().getAttribute("record_day"); %>
<% String mark = (String)request.getSession().getAttribute("mark"); %>

<% Calendar toppageCalendar = (Calendar)request.getAttribute("toppageCalendar"); %>
<% Calendar today = (Calendar)request.getAttribute("today"); %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>今日も勉強おつかれさまでした！！！！！</h2>


        	<div id="container">
        		<%--押下すると、前月や翌月のカレンダーも表示できるリンクを作成 --%>
			    <p>
					<a href="?year=<%=toppageCalendar.get(Calendar.YEAR)%>&month=<%=toppageCalendar.get(Calendar.MONTH)+1-1%>">前月</a>
			    	<a href="?year=<%=toppageCalendar.get(Calendar.YEAR)%>&month=<%=toppageCalendar.get(Calendar.MONTH)+1+1%>">翌月</a>
			    </p>

			    <%--トップページに表示されているカレンダーがいつのカレンダーなのかの表示 --%>
			    <h3><%=toppageCalendar.get(Calendar.YEAR) %>年<%=toppageCalendar.get(Calendar.MONTH)+1 %>月のカレンダー</h3>

			</div>


  			<%--トップページにメインで表示されるカレンダーのテーブルを作成し、TopPageIndexServletで作成した、日付のデータを保持する ArrayListをはめ込んでいく --%>
  			<table id="calendar">
  				<tr id="week_day">
				<th>日</th><th>月</th><th>火</th><th>水</th><th>木</th><th>金</th><th>土</th>
  				</tr>

  				<%
  				SimpleDateFormat sdf = new SimpleDateFormat("d");
  				SimpleDateFormat s_d_f = new SimpleDateFormat("yyyyMMdd");
  				%>
  				<% for (int i = 0; i < date_table.size(); i++) { %>
  				<%if (i % 7 == 0) { %>
  				<tr>
  				<% } %>
  				<td>
  					<% if (date_table.get(i) != null) { %>
  						<%=sdf.format(date_table.get(i).getTime()) %>
  							<% for (int j = 0; j < study_date.size(); j++) { %>
  								<%--今日の日付と、勉強した日付が同じなら → その日付の欄に、学習時間に対応した印を表示させる --%>
								<% if (sdf.format(date_table.get(i).getTime()).equals(sdf.format(study_date.get(j).getStudy_date()))) { %>
									<%=study_date.get(j).getStudyMark() %>
								<% } %>
  							<% } %>
  							<%--今日の日付の欄には「*」の印を表示させる --%>
						<% if (s_d_f.format(date_table.get(i).getTime()).equals(s_d_f.format(new Date()))) { %>*<% } %>
					<% } %>
  				</td>
  				<% if ((i+1) % 7 == 0) { %>
  				</tr>
  				<% } %>
  				<% } %>

  			</table>
  			<br>


  			<%--学習記録をするページに飛ぶためのリンクを作成 --%>
  			<h4 id="today_record">
  				今日（<%=today.get(Calendar.YEAR) %>年<%=today.get(Calendar.MONTH)+1%>月<%=today.get(Calendar.DATE) %>日）の
  				<a href="<c:url value='/daily' />">学習時間を記録する</a>
  			</h4>



    </c:param>
</c:import>