<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3 class="mb-3">사원 목록</h3>
<div class="row mb-2 justify-content-between">
    <div class="col-auto d-flex align-items-center">
        <label class="me-2 w-px60">부서명</label>
        <form method="post" action="list">
        <select name="department_id" class="form-select" onchange="submit()">
            <option value="-1">전체</option>
            <c:forEach items="${departments}" var="d">
                <option <c:if test="${department_id eq d.department_id}">selected</c:if>
                 value="${d.department_id}">${d.department_name}</option>

            </c:forEach>
        </select>
        </form>
    </div>
    <div class="col-auto">
        <a class="btn btn-outline-info" href="register">신규 사원 등록</a>
    </div>
</div>


<table class="table tb-list">
    <colgroup>
        <col width="80px">
        <col width="250px">
        <col width="300px">
        <col>
        <col width="120px">
    </colgroup>
    <thead>
    <tr>
        <th>사번</th>
        <th>사원명</th>
        <th>부서</th>
        <th>업무</th>
        <th>입사일자</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${list}" var="vo">
        <tr>
            <td>${vo.employee_id}</td>
            <td><a class="text-link" href="info?id=${vo.employee_id}">${vo.name}</a></td>

                <%--        <td>${vo.last_name} ${vo.first_name}></td>--%>
            <td>${vo.department_name}</td>
            <td>${vo.job_title}</td>
            <td>${vo.hire_date}</td>

        </tr>
    </c:forEach>

    </tbody>
</table>
</body>
</html>
