<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2023-12-20
  Time: 오후 4:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!doctype html>

<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>회원정보 + [${method}]</h3>
<table border="1">
    <tr>
        <th>성명</th>
        <td>${name}</td>
    </tr>
    <tr>
        <th>성별</th>

        <td>${gender}</td>
    </tr>
    <tr>
        <th>이메일</th>
        <td>${email}</td>
    </tr>
    <tr>
        <th>나이</th>
        <td>${age}</td>
    </tr>
</table>
<table border="1">
    <tr>
        <th>성명</th>
        <td>${vo.name}</td>
    </tr>
    <tr>
        <th>성별</th>

        <td>${vo.gender}</td>
    </tr>
    <tr>
        <th>이메일</th>
        <td>${vo.email}</td>
    </tr>
    <tr>
        <th>나이</th>
        <td>${vo.age}</td>
    </tr>
</table>
<input type="button" value="회원가입화면" onclick="go_join()">
</body>
<script>
    function go_join(){
        location= '<c:url value="/member"/>'
    }
</script>
</html>
