<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3 class="mb-3">고객 목록</h3>

<table class="table border-1">
    <thead>
    <tr>
        <th>고객명</th>
        <th>성별</th>
        <th>이메일</th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${empty list}">
    <tr><td colspan="3">고객 목록이 없습니다.</td> </tr>
    </c:if>
    <c:forEach items="${list}" var="vo">
        <tr>
            <td>${vo.name}</td>
            <td>${vo.gender}</td>
            <td>${vo.email}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
