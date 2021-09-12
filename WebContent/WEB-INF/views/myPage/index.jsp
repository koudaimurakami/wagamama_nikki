<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
	 <c:param name="content">
	 	<h3>${user.name}さんの学習時間</h3>

	 	<h1>合計 : ${tsh}時間</h1>
	 </c:param>
</c:import>