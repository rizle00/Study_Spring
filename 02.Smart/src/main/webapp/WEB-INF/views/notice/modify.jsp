<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>

<h3 class="mb-4"> 공지 글 수정 </h3>
<form method="post" action="update" enctype="multipart/form-data">
    <input type="hidden" name="id" value="${vo.id}">
    <table class="table tb-row">
        <colgroup>
            <col width='180px'>
            <col>
        </colgroup>
        <tr>
            <th>제목</th>
            <td><input type="text" class="form-control check-empty" value="${vo.title}" name="title" title="제목" autofocus></td>
        </tr>

        <tr>
            <th>내용</th>
            <td><textarea class="form-control check-empty" name="content" title="내용">${vo.content}</textarea></td>
        </tr>
        <tr>
            <th>파일 첨부</th>
            <td>
                <div class="row">
                    <div class="col-auto d-flex file-info">
                        <label>
                            <input class="form-control" id="file-single" type="file" name="file">
                            <i class="fa-solid fa-file-circle-plus fs-2"></i>
                        </label>
                        <div class="d-flex align-items-center">
                            <span class="file-name">${vo.filename}</span>
                            <i role="button" class="${empty vo.filename ? 'd-none' :''} text-danger ms-4 fa-solid fa-trash fs-2 file-delete"></i>
                        </div>
                    </div>
                </div>
            </td>
        </tr>


    </table>
    <input type="hidden" name="filename">
    <input type="hidden" name="curPage" value="${page.curPage}">
    <input type="hidden" name="search" value="${page.search}">
    <input type="hidden" name="keyword" value="${page.keyword}">
</form>
<div class="btn-toolbar justify-content-center gap-2">
    <button class="btn btn-dark px-4" id="btn-save">저장</button>
    <button class="btn btn-outline-dark px-4" id="btn-cancel">취소</button>
</div>
<script>
    $("#btn-save").click(function () {
        if (emptyCheck()) {// 입력이 되어 있는 경우만 서브밋
            $("[name=filename]").val($(".file-name").text());
            $("form").submit();
        }
    })
    $("#btn-cancel").click(function () {
        location = "info?id=${vo.id}&curPage=${page.curPage}&search=${page.search}&keyword=${page.keyword}";
    })
</script>

</body>
</html>