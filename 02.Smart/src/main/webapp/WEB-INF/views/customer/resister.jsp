<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3 class="mb-3">신규 고객 등록</h3>
<form method="post" action="insert.cu">
    <table class="table border-1">
        <colgroup>
            <col width="150px">
            <col >
        </colgroup>

        <tr> <th> 고객명</th>
            <td> <div class="row">
                <div class="col-auto">
                    <input class="form-control" type="text" name="name" >
                </div>
            </div>
            </td>
        </tr>
        <tr> <th> 성별</th>
            <td>
                <div class="form-check form-check-inline">
                    <label class="form-check-label" >
                        <input class="form-check-input" type="radio" name="gender" value="남"  checked> 남
                    </label>
                </div>
                <div class="form-check form-check-inline">
                    <label class="form-check-label" >
                        <input class="form-check-input" type="radio" name="gender" value="여" > 여
                    </label>
                </div>
            </td>
        </tr>
        <tr> <th> 이메일</th>
            <td>
                <div class="row">
                    <div class="col-auto">
                        <input class="form-control" type="text" name="email">
                    </div>
                </div>
            </td>
        </tr>
        <tr> <th> 전화번호</th>
            <td>
                <div class="row">
                    <div class="col-auto">
                        <input class="form-control" type="text" name="phone">
                    </div>
                </div>
            </td>
        </tr>
    </table>
    <%--    form 태그 내에 있는 button 태그의 타입은 기본 submit, 타입을  button으로 지정해야 함--%>
    <div class = "btn-toolbar justify-content-center gap-2">
        <button class="btn btn-danger" >저장</button>
        <button type="button" class="btn btn-outline-danger" onclick="history.go(-1)" >취소</button>
        <%--    <button class="btn btn-danger" onclick="location='info.cu?id=${vo.custom_id}" >취소</button>--%>
    </div>
</form>
</body>
</html>
