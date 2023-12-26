<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3 class="mb-3">사원 정보</h3>


<table class="tb-row">
    <colgroup>
        <col width="180px">
    </colgroup>

    <tr>
        <th>사번</th>
        <td>${vo.employee_id}</td>
    </tr>
    <tr>
        <th>사원명</th>
        <td>${vo.name}</td>
    </tr>
    <tr>
        <th>이메일</th>
        <td>${vo.email}</td>
    </tr>
    <tr>
        <th>전화번호</th>
        <td>${vo.phone_number}</td>
    </tr>
    <tr>
        <th>입사일자</th>
        <td>${vo.hire_date}</td>
    </tr>
    <tr>
        <th>급여</th>
        <td><fmt:formatNumber value="${vo.salary}" groupingUsed="true"/> </td>
    </tr>
    <tr>
        <th>부서</th>
        <td>${vo.department_name}</td>
    </tr>
    <tr>
        <th>업무</th>
        <td>${vo.job_title}</td>
    </tr>
    <tr>
        <th>매니저</th>
        <td>${vo.manager_name}</td>
    </tr>

</table>
<div class = "btn-toolbar justify-content-center gap-2">
    <button class="btn btn-danger " onclick="location='list'" >사원 목록</button>
    <button class="btn btn-danger " onclick="location='modify?id=${vo.employee_id}'" >정보 수정</button>
    <button class="btn btn-danger " id = "btn-delete" >정보 삭제</button>
</div>
<script>
    $("#btn-delete").click(function (){
        if(confirm("정말 사원 [${vo.name}]을 삭제하시겠습니까?")){
            location="delete?id=${vo.employee_id}"
        }
    })
</script>
</body>
</html>
