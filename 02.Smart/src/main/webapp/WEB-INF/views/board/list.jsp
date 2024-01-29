<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>

<h3 class="mb-4"> 방명록 목록 </h3>
<form method="post" action="list">
    <input type="hidden" name="curPage" value="1">
    <div class="row mb-2 justify-content-sm-between">
        <div class="col-auto">
            <div class="input-group">
                <select name="search" class="form-select" style="width: 130px">
                    <option value="s1" ${page.search eq "s1"? "selected" : ""}>전체</option>
                    <option value="s2" ${page.search eq "s2"? "selected" : ""}>제목</option>
                    <option value="s3" ${page.search eq "s3"? "selected" : ""}>내용</option>
                    <option value="s4" ${page.search eq "s4"? "selected" : ""}>작성자</option>
                    <option value="s5" ${page.search eq "s5"? "selected" : ""}>제목+내용</option>
                </select>
                <input type="text" name="keyword" class="form-control" value="${page.keyword}">
                <button class="btn btn-primary"><i class="fa-solid fa-magnifying-glass"></i></button>
            </div>
        </div>
        <%--   로그인 되어 있는 경우만--%>
        <c:if test="${!empty loginInfo}">
            <div class="col-auto">
                <button type="button" class="btn btn-outline-primary" onclick="location='register'">글쓰기</button>
            </div>
        </c:if>
    </div>
</form>

<table class="table tb-list">
    <colgroup>
        <col width='100px'>
        <col>
        <col width='120px'>
        <col width='120px'>
        <col width='100px'>
        <col width='100px'>
    </colgroup>
    <tr>
        <th>번호</th>
        <th>제목</th>
        <th>작성자</th>
        <th>작성일자</th>
        <th>조회수</th>
        <th>첨부파일</th>
    </tr>

    <c:if test="${empty page.list}">
        <tr>
            <td colspan="6">방명록 글이 없습니다</td>
        </tr>
    </c:if>
    <c:if test="${not empty page.list}">
    <c:forEach items="${page.list}" var="vo">
    <tr>
        <td>${vo.no}</td>
        <td class="text-start"><a class="text-link"
                                  href="info?id=${vo.id}&curPage=${page.curPage}&search=${page.search}&keyword=${page.keyword}">
                ${vo.title}</a></td>
        <td>${vo.name}</td>
        <td>${vo.writedate}</td>
        <td>${vo.readcnt}</td>
<%--        <td><c:if test="${!empty vo.fileList.filename}"><i class="fa-solid fa-paperclip"></i></c:if></td>--%>
    </tr>
    </c:forEach>
    </c:if>

</table>
<jsp:include page="/WEB-INF/views/include/page.jsp"/>
</body>
</html>