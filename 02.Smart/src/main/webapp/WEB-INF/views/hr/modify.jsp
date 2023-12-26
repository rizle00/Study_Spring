<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3 class="mb-3">사원 정보 수정</h3>

<form method="post" action="update" >
<table class="tb-row">
    <colgroup>
        <col width="180px">
    </colgroup>

    <tr>
        <th>사번</th>
        <td>${vo.employee_id}</td>
        <input type="hidden" name="employee_id" value="${vo.employee_id}"/>
    </tr>
    <tr>
        <th>사원명</th>
        <td>
            <div class="row">
                <div class="col-auto">
                    <input class="form-control" type="text" name="last_name" value="${vo.last_name}" placeholder="성">
                </div>
                <div class="col-auto">
                    <input class="form-control" type="text" name="first_name" value="${vo.first_name}" placeholder="명">
                </div>
            </div>
        </td>
    </tr>
    <tr>
        <th>이메일</th>
        <td>
            <div class="row">
                <div class="col-auto">
                    <input class="form-control" type="text" name="email" value="${vo.email}">
                </div>
            </div>

        </td>
    </tr>
    <tr>
        <th>전화번호</th>
        <td>
            <div class="row">
                <div class="col-auto">
                    <input class="form-control" type="text" name="phone_number" value="${vo.phone_number}">
                </div>
            </div>
        </td>
    </tr>
    <tr>
        <th>입사일자</th>
        <td>
            <div class="row">
                <div class="col-auto">
                    <input class="form-control date" type="text" name="hire_date" value="${vo.hire_date}">
                </div>
            </div>
        </td>
    </tr>
    <tr>
        <th>급여</th>
        <td>
            <div class="row">
                <div class="col-auto">
                    <input class="form-control" type="text" name="salary" value="${vo.salary}">
                </div>
            </div>
        </td>
    </tr>
    <tr>
        <th>부서</th>
        <td>
            <div class="row">
                <div class="col-auto">
                    <select name ="department_id" class="form-select">
                        <option value="-1">소속없음</option>
                        <c:forEach items="${departments}" var="d">
                        <option value="${d.department_id}"
                            ${vo.department_id eq d.department_id? "selected" :""}>
                                ${d.department_name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </td>
    </tr>
    <tr>
        <th>업무</th>
        <td>
            <div class="row">
            <div class="col-auto">
                <select name ="job_id">
                    <c:forEach items="${jobs}" var="j">
                        <option value="${j.job_id}" ${j.job_id eq vo.job_id ? "selected" : ""}> ${j.job_title}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        </td>
    </tr>

</table>
</form>
<div class="btn-toolbar justify-content-center gap-2">
    <button class="btn btn-danger " onclick="$('form').submit()">저장</button>
    <button class="btn btn-outline-danger " id="btn-cancel">취소</button>
</div>
<script>
    $("#btn-cancel").on('click',function (){
        location="info?id=${vo.employee_id}"
    })

</script>
</body>
</html>
