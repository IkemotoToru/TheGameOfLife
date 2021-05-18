<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.List,model.UserRegistrationBeans,model.RankingBeans,model.NowPlayer"%>
<% String msg = (String) session.getAttribute("msg"); %>
<% String name = (String) session.getAttribute("name"); %>
<% String pass = (String) session.getAttribute("pass"); %>
<% Boolean count = (Boolean) session.getAttribute("count"); %>
<% NowPlayer nowPlayer =  (NowPlayer) session.getAttribute("nowPlayer");%>
<% List<RankingBeans> pRanking =  (List<RankingBeans>) request.getAttribute("pRanking");%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<%if( pRanking == null){ %>
	<meta http-equiv="refresh" content="0; URL=/TheGameOfLife/Ranking">
	<% } %>
	<link href="css/style.css" rel="stylesheet" />
	<style>
		body {
			background-image: url("image/indexSpace.gif");
			background-size: cover;
			background-attachment: fixed;
			background-position: center center;
		}
	</style>
	<title>The Game Of Life</title>
</head>
<body>
	<% if(nowPlayer != null){ %>
	<header>
		<form action="/TheGameOfLife/Inquiries" method="post">
		<input type="submit" value="問い合わせ・感想はこちら">
		</form>
	</header>
	<% } %>

	<p style="text-align:center">
	<img alt="人生ゲーム" src="image/logo.png" class="logo">

	<%--メッセ―時表示 ⑦--%>
	<p style="text-align:center">
	<font color="#fafdff">
		<%if(msg != null){ %>
		<%= msg %>
		<% } %>
	</font></p>

	<form action="/TheGameOfLife/Index" method="post">
		<p style="text-align:center"><% if(name == null){ %><input type="text" name="name"placeholder="UserName"><% }else{ %><font color="#fafdff"><%= name %><% } %></font></p>
		<p style="text-align:center"><% if(pass == null){ %><input type="text" name="pass"placeholder="PASSWORD"><% }else{ %><font color="#fafdff"><%= pass %><% } %></font></p>

		<% if(name == null || pass == null){ %>

		<%--ログイン --%>
		<p style="text-align:center">
		<input type="submit"  value="ログイン">
	</form>
	<form action="/TheGameOfLife/UserRegistration" method="get">
		<%--ユーザー登録 --%>
		<p style="text-align:center">
		<input type="submit" value="ユーザー登録">
		<% } %>
	</form>

	<% if(count != null){ %>
		<% if(count){ %>
			<form action="/TheGameOfLife/Game" method="get">

			<%--ログイン時ゲームスタート表示 --%>
			<p style="text-align:center">
			<button type="submit"onclick="" value="スタート" name="game">ゲームスタート</button>

			<%--ルール説明 --%>
			<button type="submit"onclick="" value="ルール" name="game">ルール説明</button></p>

			</form>
			<p style="text-align:center">
			<a href="/TheGameOfLife/Index">LogOut</a>
			</p>
		<% } %>
	<% } %>
	<%if(pRanking != null){ %>
	<table border="1" class="top_ranking">
		<tr><th>順位</th><th>日にち</th><th>名前</th><th>最終金額</th></tr>
		<% for(int i = 0; i<pRanking.size() ; i++){ %>
		<% String money = String.format("%,d", pRanking.get(i).getPlayer_money()); %>
		<tr><td><%= i+1 %></td>
			<td><%= pRanking.get(i).getPlayer_date() %></td>
			<td><%= pRanking.get(i).getPlayer_name() %></td>
			<td><%= money %>円</td>
		</tr>
		<% } %>
	</table>
	<% } %>
</body>
</html>