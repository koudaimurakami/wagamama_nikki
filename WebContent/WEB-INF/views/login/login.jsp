<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${hasError}">
            <div id="flush_error">
                ログインIDかパスワードが間違っています。
            </div>
        </c:if>
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <div id="right_side">
        	<h2 id="introduction_title">「わがまま日記」とは...
        		<p>学習時間を記録するためのアプリです！</p>
        	</h2>

        	<div class="box">
        		<h4 id="introduction_content">こんな人におすすめ
       			<p>
       				✓毎日勉強を継続して頑張っている人<br>
       				✓学習記録を毎日つけるのが苦手な人<br>
       				✓でも学習の足跡を見える化したい人
       			</p>
     			</h4>
        	</div>

        </div>




        <div id="left_side">
        	<h2>ログイン画面</h2>
        	 <form method="POST" action="<c:url value='/login' />">
            <label for="login_id">ログインID</label><br />
            <input type="text" name="login_id" value="${login_id}" />
            <br /><br />

            <label for="password">パスワード</label><br />
            <input type="password" name="password" />
            <br /><br />

            <input type="hidden" name="_token" value="${_token}" />
            <button type="submit">ログイン</button>
        </form>
        <br><br><br>

        <h4 id="sign_up">
        	↓初めての方はこちら↓<br>
        	<a href="<c:url value='/users/new' />">ユーザー登録してはじめる</a>
        </h4>
        </div>

        <div class="clear"></div>

    </c:param>
</c:import>