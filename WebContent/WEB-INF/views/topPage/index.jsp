<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="models.Calendars, java.util.ArrayList, java.util.Calendar, java.text.SimpleDateFormat, java.util.Date" %>
<% Calendars cls=(Calendars)request.getAttribute("cls"); %>
<%
@SuppressWarnings("unchecked")
ArrayList<Calendar> dates = (ArrayList<Calendar>)request.getAttribute("dates");
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>今日も勉強おつかれさまでした！</h2>


        	<div id="container">
			    <h3><%=cls.getYear() %>年<%=cls.getMonth() %>月のカレンダー</h3>
			    <p>
					<a href="?year=<%=cls.getYear()%>&month=<%=cls.getMonth()-1%>">前月</a>
			    	<a href="?year=<%=cls.getYear()%>&month=<%=cls.getMonth()+1%>">翌月</a>
			    </p>
			    <table>
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
  			<%--パターン① --%>
  				<h3>${daily}の学習記録</h3>
			<%--パターン② --%>
  				<h4>の勉強時間</h4>
  				<a href="<c:url value='/daily' />">学習時間を記録する</a>
  			</div>




  			<table>
  				<tr>
				<th>日</th><th>月</th><th>火</th><th>水</th><th>木</th><th>金</th><th>土</th>
  				</tr>

  				<tr>
  				<%
  				SimpleDateFormat sdf = new SimpleDateFormat("d");
  				%>
  				<% for (int i = 0; i < dates.size(); i++) { %>
  				<td>
  					<% if (dates.get(i) != null) { %>
  						<%=sdf.format(dates.get(i).getTime()) %>
					<% } %>
					<% if (dates.get(i) != null && sdf.format(dates.get(i).getTime()).equals(sdf.format(new Date()))) { %>*<% } %>
  				</td>
  				<% if ((i+1) % 7 == 0) { %>
  				</tr>
  				<tr>
  				<% } %>
  				<% } %>
  				</tr>

  			</table>

    </c:param>
</c:import>