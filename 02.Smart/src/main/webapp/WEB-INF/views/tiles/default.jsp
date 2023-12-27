<%@ page import="java.util.Date" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!doctype html>

<html>
<head>

	<link rel="icon" type="image/x-icon" href="<c:url value='//img/hanul.ico' />" />
	<!-- Core theme CSS (includes Bootstrap)-->
	<link href="<c:url value='/css/styles.css'/>" rel="stylesheet" />
	<link href="<c:url value='/css/common.css/'/>?<%=new java.util.Date()%>" rel="stylesheet" />
	<link rel="stylesheet" href="//code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
<%-- jquery  를 common js  에서 쓰고 있기 때문에 먼저 선언해줘야 common에서 사용 가능, 읽는 순서 중요--%>
	<script type="text/javascript" src="https://code.jquery.com/jquery-3.7.1.min.js" ></script>
	<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
	<script src="<c:url value='/js/common.js'/>?<%= new java.util.Date()%>"></script>

	<%-- category : cu -> 고객관리
				hr -> 사원관리...--%>
	<c:choose>
		<c:when test="${category eq 'login'}">
			<c:set var="title" value="_로그인"/>
		</c:when>
		<c:when test="${category eq 'find'}">
			<c:set var="title" value="_비밀번호찾기"/>
		</c:when>

	</c:choose>
	<title>스마트 IoT 융합${title}</title>
</head>
<body class="bg-primary">

		<!-- Page content-->
		<div class="container-fluid my-3 mx-3">
			<tiles:insertAttribute name="container"/>
		</div>


</body>
</html>
