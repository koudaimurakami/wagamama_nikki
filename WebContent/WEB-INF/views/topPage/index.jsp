<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="models.Study, java.util.ArrayList, java.util.Calendar, java.text.SimpleDateFormat, java.util.Date, java.util.List" %>
<%
@SuppressWarnings("unchecked")
ArrayList<Calendar> dates = (ArrayList<Calendar>)request.getAttribute("dates");
%>
<%
@SuppressWarnings("unchecked")
List<Study> study_date =  (List<Study>)request.getAttribute("study_date"); %>

<% Date record_day = (Date)request.getSession().getAttribute("record_day"); %>
<% String mark = (String)request.getSession().getAttribute("mark"); %>

<% Calendar today = (Calendar)request.getAttribute("today"); %>
<% Calendar daily = (Calendar)request.getAttribute("daily"); %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>今日も勉強おつかれさまでした！！！！！</h2>


        	<div id="container">
			    <p>
					<a href="?year=<%=today.get(Calendar.YEAR)%>&month=<%=today.get(Calendar.MONTH)+1-1%>">前月</a>
			    	<a href="?year=<%=today.get(Calendar.YEAR)%>&month=<%=today.get(Calendar.MONTH)+1+1%>">翌月</a>
			    </p>

			    <h3><%=today.get(Calendar.YEAR) %>年<%=today.get(Calendar.MONTH)+1 %>月のカレンダー</h3>

			</div>


			    <%-- <table>
			      <tr>
			        <th>日</th>
			        <th>月</th>
			        <th>火</th>
			        <th>水</th>
			        <th>木</th>
			        <th>金</th>
			        <th>土</th>
			      </tr>
			      <%for(String[] row: cls.getDate()){ %>
    			  <tr>
      				<%for(String col:row) {%>
      					<%if (col.startsWith("●")){ %>
      						<td class="today"><%= col %></td>
      					<%}else{ %>
      						<td><%=col %></td>
      					<%} %>
      				<%} %>
      			   </tr>
      			  <%} %>
			      </table>
  			</div><!-- end container-->
  			<div id="record">
			     --%>










  			<table id="calendar">
  				<tr>
				<th>日</th><th>月</th><th>火</th><th>水</th><th>木</th><th>金</th><th>土</th>
  				</tr>

  				<%
  				SimpleDateFormat sdf = new SimpleDateFormat("d");
  				SimpleDateFormat s_d_f = new SimpleDateFormat("yyyyMMdd");
  				%>
  				<% for (int i = 0; i < dates.size(); i++) { %>
  				<%if (i % 7 == 0) { %>
  				<tr>
  				<% } %>
  				<td>
  					<% if (dates.get(i) != null) { %>
  						<%=sdf.format(dates.get(i).getTime()) %>
  							<% for (int j = 0; j < study_date.size(); j++) { %>
								<% if (sdf.format(dates.get(i).getTime()).equals(sdf.format(study_date.get(j).getStudy_date()))) { %>
									<%=study_date.get(j).getStudyMark() %>
								<% } %>
  							<% } %>
						<% if (s_d_f.format(dates.get(i).getTime()).equals(s_d_f.format(new Date()))) { %>*<% } %>
					<% } %>
  				</td>
  				<% if ((i+1) % 7 == 0) { %>
  				</tr>
  				<% } %>
  				<% } %>

  			</table>
  			<br>


  			<h4 id="today_record">
  				今日（<%=daily.get(Calendar.YEAR) %>年<%=daily.get(Calendar.MONTH)+1%>月<%=daily.get(Calendar.DATE) %>日）の
  				<a href="<c:url value='/daily' />">学習時間を記録する</a>
  			</h4>



    </c:param>
</c:import>