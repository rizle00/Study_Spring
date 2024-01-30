<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>

<h3 class="mb-4"> 방명록 등록 </h3>
<form method="post" action="insert" enctype="multipart/form-data">
    <input type="hidden" name="writer" value="${loginInfo.user_id}">
    <table class="table tb-row">
        <colgroup>
            <col width='180px'>
            <col>
        </colgroup>
        <tr>
            <th>제목</th>
            <td><input type="text" class="form-control check-empty" name="title" title="제목" autofocus></td>
        </tr>

        <tr>
            <th>내용</th>
            <td><textarea class="form-control check-empty" name="content" title="내용"></textarea></td>
        </tr>
        <tr>
            <th>파일 첨부</th>
            <td>
                <div class="row">
                    <div class="col-auto d-flex file-info">
                        <label>
                            <input class="form-control" id="file-multiple" type="file" name="file" multiple>
                            <i role="button" class="fa-solid fa-file-circle-plus fs-2"></i>
                        </label>
                        <div class="form-control py-2 mt-2 file-drag">
                            <div class="py-3 text-center">첨부할 파일을 마우스로 끌어오세요</div>
                        </div>
<%--                        <div class="d-flex align-items-center">--%>
<%--                            <span class="file-name"></span>--%>
<%--                            <i role="button" class="d-none text-danger ms-4 fa-solid fa-trash fs-2 file-delete"></i>--%>
<%--                        </div>--%>
                    </div>
                </div>
            </td>
        </tr>


    </table>
</form>
<div class="btn-toolbar justify-content-center gap-2">
    <button class="btn btn-dark px-4" id="btn-save">저장</button>
    <button class="btn btn-outline-dark px-4" id="btn-cancel">취소</button>
</div>
<script>
    var fileList = new FileList();
    // var test ="abc";
    // var tag = `<span>\${test}</span>`백틱
    $("#btn-save").click(function () {

        return;
        if (emptyCheck()) {
            // 입력이 되어 있는 경우만 서브밋
            multipleFileUpload();
            $("form").submit();
        }
    })
    $("#btn-cancel").click(function () {
        location = "list";
    })
</script>

</body>
</html>