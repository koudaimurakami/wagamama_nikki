<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>学習時間  記録ページ</h2>

        <form method="POST" action="<c:url value='/record' />">
            <c:import url="_form.jsp" />
        </form>

        <p><a href="<c:url value='/index.html' />">トップページに戻る</a></p>
    </c:param>
</c:import>