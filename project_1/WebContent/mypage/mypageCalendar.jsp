<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title> 내 달력</title>
	<link href="style.css" rel="stylesheet" type="text/css" >
</head>



<body>
	<!-- 전체 -->
	<div class="wrap">
	<!-- 헤더블럭 -->
	<%@ include file="../common/header.jsp" %>
	<!-- 컨텐츠블럭 -->
	<div class="container">
	<%@ include file="/mypage/mypageLeft.jsp" %>
		<h1>내 달력</h1> 
		<hr>
		<img src="imgs/preparing.png" width="50"/>
		
		 
	</div>
	</div>
	<!-- 풋터블럭 -->
	<%@ include file="../common/footer.jsp" %>
</body>
</html>