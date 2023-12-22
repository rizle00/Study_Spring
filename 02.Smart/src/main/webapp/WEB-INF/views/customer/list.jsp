<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3 class="mb-3">고객 목록</h3>
<form method="post" action="list.cu">
    <div class="row mb-3 justify-content-between">
        <div class="col-auto">
            <div class="input-group">
                <label class="col-form-label me-2">고객명</label>
                <input type="text" name="name" class="form-control" value="${name}">
                <button class="btn btn-primary">검색</button>
            </div>
        </div>
        <div class="col-auto">
            <button type="button" class="btn btn-outline-primary" onclick="location='resister.cu'">고객등록</button>
        </div>
    </div>
</form>
<table class="table tb-list">
    <thead>
    <tr>
        <th>고객명</th>
        <th>성별</th>
        <th>이메일</th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${empty list}">
        <tr>
            <td colspan="3">고객 목록이 없습니다.</td>
        </tr>
    </c:if>
    <c:forEach items="${list}" var="vo">
        <tr>
            <td><a href="info.cu?id=${vo.customer_id}"> ${vo.name}</a></td>
            <td>${vo.gender}</td>
            <td>${vo.email}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
