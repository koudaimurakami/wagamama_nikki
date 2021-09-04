<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="models.Calendars" %>
<% Calendars cls=(Calendars)request.getAttribute("cls"); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>今日も勉強おつかれさまでした！</h2>
         <div id="container">
    <h1><%=cls.getYear() %>年<%=cls.getMonth() %>月のカレンダー</h1>
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
      		<%if (col.startsWith("*")){ %>
      			<td class="today"><%=col.substring(1)%></td>
      		<%}else{ %>
      			<td><%=col %></td>
      		<%} %>
      	<%} %>
      </tr>
      <%} %>
    </table>
  </div><!-- end container-->
    </c:param>
</c:import>