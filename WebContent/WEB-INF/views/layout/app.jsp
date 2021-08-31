<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
    <head>
        <meta charset="UTF-8">
        <title>わがまま日記</title>
        <link rel="stylesheet" href="<c:url value='/css/reset.css' />">
        <link rel="stylesheet" href="<c:url value='/css/style.css' />">
    </head>
    <body>
    	 <div id="wrapper">
            <div id="header">
                <h1>わがまま日記</h1>
            </div>
            <div id="content">
                ${param.content}
            </div>
            <div id="footer">
                by Kodai Murakami.
            </div>
        </div>
    </body>
</html>