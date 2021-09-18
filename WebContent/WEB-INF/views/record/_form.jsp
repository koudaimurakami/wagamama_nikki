<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h3>学習時間  記録ページ</h3>
<table>
	<tr>
		<th rowspan = "3">☆</th>
		<td>
			<button
				type = "submit"
				name = "stu_hour"
				value = "10"
			>10時間</button>
		</td>
		<td rowspan = "3">これ以上にない頑張りです！ゆっくり休んでください！</td>
	</tr>
	<tr>
		<%--共通部分なので、thタグは書かない --%>
		<td>
			<button
				type = "submit"
				name = "stu_hour"
				value = "9"
			>9時間</button>
		</td>
		<%--共通部分なので、ここのtdタグは書かない --%>
	</tr>
	<tr>
		<%--共通部分なので、thタグは書かない --%>
		<td>
			<button
				type = "submit"
				name = "stu_hour"
				value = "8"
			>8時間</button>
		</td>
		<%--共通部分なので、ここのtdタグは書かない --%>
	</tr>


	<tr>
		<th rowspan = "4">◎</th>
		<td>
			<button
				type = "submit"
				name = "stu_hour"
				value = "7"
			>7時間</button>
		</td>
		<td rowspan = "4">素晴らしいです！お疲れさまでした！</td>
	</tr>
	<tr>
		<%--共通部分なので、thタグは書かない --%>
		<td>
			<button
				type = "submit"
				name = "stu_hour"
				value = "6"
			>6時間</button>
		</td>
		<%--共通部分なので、ここのtdタグは書かない --%>
	</tr>
	<tr>
		<%--共通部分なので、thタグは書かない --%>
		<td>
			<button
				type = "submit"
				name = "stu_hour"
				value = "5"
			>5時間</button>
		</td>
		<%--共通部分なので、ここのtdタグは書かない --%>
	</tr>
	<tr>
		<%--共通部分なので、thタグは書かない --%>
		<td>
			<button
				type = "submit"
				name = "stu_hour"
				value = "4"
			>4時間</button>
		</td>
		<%--共通部分なので、ここのtdタグは書かない --%>
	</tr>


	<tr>
		<th rowspan = "3">○</th>
		<td>
			<button
				type = "submit"
				name = "stu_hour"
				value = "3"
			>3時間</button>
		</td>
		<td rowspan = "3">すごいです！これを毎日積み重ねましょう！</td>
	</tr>
	<tr>
		<%--共通部分なので、thタグは書かない --%>
		<td>
			<button
				type = "submit"
				name = "stu_hour"
				value = "2"
			>2時間</button>
		</td>
		<%--共通部分なので、ここのtdタグは書かない --%>
	</tr>
	<tr>
		<%--共通部分なので、thタグは書かない --%>
		<td>
			<button
				type = "submit"
				name = "stu_hour"
				value = "1"
			>1時間</button>
		</td>
		<%--共通部分なので、ここのtdタグは書かない --%>
	</tr>


	<tr>
		<th>♪</th>
		<td>
			<button
				type = "submit"
				name = "stu_hour"
				value = "0"
			>リフレッシュ</button>
		</td>
		<td>最高です！リフレッシュがあるから勉強が続くんです！</td>
	</tr>



</table>