<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>

<h3 class="mb-4"> 방명록 정보 </h3>
<table class="table tb-row">
    <colgroup>
        <col width='180px'>
        <col>
        <col width='120px'>
        <col width='120px'>
        <col width='100px'>
        <col width='80px'>
    </colgroup>
    <tr>
        <th>제목</th>
        <td colspan="5">${vo.title}</td>
    </tr>
    <tr>
        <th>작성자</th>
        <td>${vo.name}</td>
        <th>작성일자</th>
        <td>${vo.writedate}</td>
        <th>조회수</th>
        <td>${vo.readcnt}</td>
    </tr>

    <tr>
        <th>내용</th>
        <td colspan="5">${fn: replace(vo.content, crlf, "<br>")}</td>
    </tr>
    <tr>
        <th>첨부파일</th>
        <td colspan="5">
<%--            <c:if test="${!empty vo.filename}">--%>
<%--                <div class="row">--%>
<%--                    <div class="col-auto">--%>
<%--                        <span> ${vo.filename}</span> <i role="button" class="file-download fa-solid fa-download ms-2"></i>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </c:if>--%>
        </td>
    </tr>


</table>
<div class="btn-toolbar justify-content-center gap-2">
    <button class="btn btn-primary" id="btn-list">목록으로</button>
<%--    작성자로 로그인한 경우만 수정/삭제 보이게--%>
    <c:if test="${loginInfo.user_id eq vo.writer}">
<%--    <c:if test="${loginInfo.role eq 'admin'}">--%>
    <button class="btn btn-primary" id="btn-modify">정보수정</button>
    <button class="btn btn-primary" id="btn-delete">정보삭제</button>
    </c:if>

</div>
<c:set var="params" value="curPage=${page.curPage}&search=${page.search}&keyword=${page.keyword}"/>
<script>
    $("#btn-list").click(function () {
        location = "list?${params}";
    });

    $("#btn-modify").click(function () {
        location = "modify?id=${vo.id}&${params}"
    });

    $("#btn-delete").click(function () {
       if(confirm("정말 삭제 하시겠습니까?")){
           location = "delete?id=${vo.id}&${params}"
       }
    });

    $(".file-download").click(function (){
        location="download?id=${vo.id}";
    })

</script>

</body>
</html>