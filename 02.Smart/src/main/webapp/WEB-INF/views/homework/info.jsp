<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3 class="mb-3">고객 정보</h3>

<table class="table tb-row">
    <colgroup>
        <col width="150px">
        <col >
    </colgroup>

    <tr> <th> 고객명</th>
            <td>${vo.name}</td>
    </tr>
    <tr> <th> 성별</th>
        <td>${vo.gender}</td>
    </tr>
    <tr> <th> 이메일</th>
        <td>${vo.email}</td>
    </tr>
    <tr> <th> 전화번호</th>
        <td>${vo.phone}</td>
    </tr>
</table>
<div class = "btn-toolbar justify-content-center">
    <button class="btn btn-danger mx-2" onclick="location='list'">고객목록</button>
</div>

</body>
</html>
