<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
<html>
<head>
	<script type="text/javascript" src="https://code.jquery.com/jquery-3.7.1.min.js" ></script>
	<title>Home</title>
</head>
<body>
<h1>
	스마트 IoT 미들웨어 서버프로그램
</h1>
<h3>고객 관리</h3>
<div><a target="_blank" href ="<c:url value='/customer/list'/>" >전체 목록 조회</a></div>
<div><input type="text" id="query">
	<a target="_blank" href ="" id="search" >검색 목록 조회</a></div>
<hr>
<div>
	<input type="text" id="id"><a target="_blank" id="info" href="">고객 조회</a>
</div>
<div>
	<a target="_blank" id="delete" href="">고객 정보 삭제</a>
</div>

<div>고객번호 : <input type="text" id="customer_id" value="23"></div>
<div>고객명 : <input type="text" id="name" value="a223415a"></div>
<div>성별 : <input type="text" id="gender" value="남"></div>
<div>이메일 : <input type="text" id="email" value="aaa@asd"></div>
<div>전화번호 : <input type="text" id="phone" value="1245"></div>
<div><button id="change">정보 변경</button> </div>
<div><button id="resister">신규등록</button> </div>

<hr>
<h3>회원관리</h3>
<div>
	아이디:<input type="text" id="user_id">
	비번:<input type="password" id="user_pw">
	<a href="" target="_blank" id="login">로그인</a>
</div>
<script>
	$("#search").click(function (){
		$(this).attr("href", "<c:url value='/customer/list'/>?query="+$("#query").val() );
	})

	$("#info").click(function (){
		$(this).attr("href","<c:url value='/customer/info'/>?id="+$("#id").val() )
	})

	$("#delete").click(function () {
		$(this).attr("href","<c:url value='/customer/delete'/>?id="+$("#id").val())
	})

	$("#change").click(function () {
	// 	json 형태의 문자열 만들기{key : value}
		var Customer = {}; // new Object();
		Customer.customer_id = $("#customer_id").val();
		Customer.name = $("#name").val();
		Customer.gender = $("#gender").val();
		Customer.email = $("#email").val();
		Customer.phone = $("#phone").val();

		$.ajax({
			url:"<c:url value='/customer/update'/>",
			data: {vo: JSON.stringify(Customer)}
		}).done(function (){

		});


	})

	$("#resister").click(function (){
		var Customer = new Object(); // new Object();
		Customer.name = $("#name").val();
		Customer.gender = $("#gender").val();
		Customer.email = $("#email").val();
		Customer.phone = $("#phone").val();

		$.ajax({
			url:"<c:url value='/customer/insert'/>",
			data: {vo: JSON.stringify(Customer)}
		}).done(function (){

		})
	})

	$("#login").click(function (){
		$(this).attr("href", "<c:url value='/member/login'/>?user_id="+$("#user_id").val()+"&user_pw="+$("#user_pw").val());
	})
</script>
</body>
</html>