<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
    <head>
        <meta charset="UTF-8">
        <title>わがまま日記</title>
        <link rel="stylesheet" href="<c:url value='/css/reset.css' />" media="all">
        <link rel="stylesheet" href="<c:url value='/css/style.css' />" media="all">
    </head>
    <body>
    	 <div id="wrapper">

            <div id="header">

            	<div id="header_menu">
            		 <h1>わがまま日記</h1>&nbsp;&nbsp;&nbsp;
            		 <c:if test="${sessionScope.login_user != null}">
                     	<a href="<c:url value='/index.html' />">トップページ</a>&nbsp;
                        <a href="<c:url value='/mypage' />">マイページ</a>&nbsp;
                 	 </c:if>
                 </div>

                 <c:if test="${sessionScope.login_user != null}">
                 <div id="user_name">
                 	<c:out value="${sessionScope.login_user.name}" />&nbsp;さん&nbsp;&nbsp;&nbsp;
                 	<a href="<c:url value='/logout' />">ログアウト</a>
                 </div>
                 </c:if>

	         </div>

	         <div id="content">
	                ${param.content}
	            </div>

	         <div id="footer">
	                ©2021 - kodai murakami.
	         </div>

	       </div>
	    </body>
	</html>