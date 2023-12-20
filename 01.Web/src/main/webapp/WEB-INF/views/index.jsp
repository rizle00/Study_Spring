<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2023-12-20
  Time: 오후 2:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<a href="<c:url value='/'/>">홈으로</a>
<hr>
    <h3>첫번째 테스트 결과(${type} 객체 사용)</h3>
    <div>오늘 날짜 : ${today}</div>
    <div>현재시간 : ${now}</div>

</body>
</html>
