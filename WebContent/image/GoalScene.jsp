<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.NowPlayer" %>
<%@ page import="model.RankingBeans" %>
<%@ page import ="dao.playerDAO" %>

<% NowPlayer nowPlayer = (NowPlayer)session.getAttribute("nowPlayer"); %>
<% String msg = (String)session.getAttribute("msg"); %>
<% List<RankingBeans> pRanking =  (List<RankingBeans>) request.getAttribute("pRanking");%>



<!DOCTYPE html>
<html>
<head>
<style>
body {
	background-image: url("image/haikei.gif");
	background-size: cover;
	background-attachment: fixed;
	background-repeat: no-repeat;
}

table th{/*thに対して*/
	color: #ffffff;/*文字色*/
	background: #000000;/*背景色*/
}
table td{/*thに対して*/
	color: #ffffff;/*文字色*/
	background: #000000;/*背景色*/
}

</style>
<link type="text/css"  href="css/style.css" rel="stylesheet"/>
<meta charset="UTF-8">
<title>GOAL!!!</title>
</head>
<body>
<header>
</header>
<img alt="右羽" src="image/hane1.png" width="200" height="100" class="hane1">
<img alt="左羽" src="image/hane2.png" width="200" height="100" class="hane2">
<p class="goal">
GOAL!!</p>
<p class="goal_msg"><%= msg %></p>


<%--ランキングボタンおしたらランキング表示 --%>

<form action="/TheGameOfLife/Ranking"  method="get" >

<% if(pRanking != null){ %>
<table border="1" class="player">
<tr><th>順位</th><th>日にち</th><th>名前</th><th>最終金額</th></tr>
	<% for(int i = 0; i<pRanking.size() ; i++){ %>
		<tr><td><%= i+1 %></td>
		<td><%= pRanking.get(i).getPlayer_date() %></td>
		<td><%= pRanking.get(i).getPlayer_name() %></td>
		<td><%= pRanking.get(i).getPlayer_money() %></td>
		</tr>
	<% } %>
</table>
<% } %>
<input type="submit" class="rankbotan" value="ランキング表示">
</form>

<br>
<p class="top_a">
<a href="index.jsp" >Topへ戻る</a>
</p>
</body>
<footer>
</footer>
</html>