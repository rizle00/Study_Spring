<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<a href="<c:url value='/'/>">홈으로</a>
<hr>
<h3> 로그인</h3>

<form method="post" action="login_result">
   <div>아이디: <input type="text" name="userid"></div>
   <div>비밀번호: <input type="password" name="userpw"></div>
   <div><input type="submit" value="로그인"></div>
</form>
</body>
</html>