<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>


<h3 class="mb-4"> 공공데이터 목록 </h3>
<ul class="nav nav-pills justify-content-center mb-3">
    <li class="nav-item">
        <a class="nav-link active" aria-current="page" href="#">약국조회</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" href="#">유기동물조회</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" href="#">기타조회</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" href="#">기타조회</a>
    </li>
</ul>
<div class="row mb-2 justify-content-sm-between mb-1">

    <div class="col-auto">
    </div>
    <div class="col-auto">
        <select id="pageList" class="form-select">
            <c:forEach var="i" begin="1" end="5">
                <option value="${10*i}">${10*i}개 씩</option>
            </c:forEach>
        </select>
    </div>
<div id="data-list">

</div>
<script>
    $("ul.nav-pills li").click(function () {
        $("ul.nav-pills li a").removeClass("active");
        $(this).children("a").addClass("active");// 클릭된 자기자신
        var index = $(this).index();
        if (index == 0) {
            pharmacy_list(1);
        } else if (index == 1) {
            animal_list();
        }
    })
    $(function () {// 다큐먼트 레디..
        $("ul.nav-pills li").eq(0).trigger("click"); //클릭 이벤트 강제 발생
    })

    $(document)
        .on("click", ".pagination li a", function () {
            pharmacy_list($(this).data("page"));// 현재페이지 바뀜처리
        })

    $("#pageList").change(function () {
        page.pageList = $(this).val();
        pharmacy_list(1);
    })
    // pharmacy_list();
    //약국 목록 조회
    function pharmacy_list(curPage) {
        var table =
            `<table class="table tb-list pharmacy">
            <colgroup>
                <col width="300px">
                    <col width="160px">
                        <col>
            </colgroup>
            <thead>
            <tr>
                <th>악국명</th>
                <th>전화번호</th>
                <th>주소</th>
            </tr>
            </thead>
            <tbody></tbody>
            </table>`;
        $("#data-list").html(table);
        $.ajax({
            url: "pharmacy",
            data: {pageNo: curPage, numOfRows : page.pageList}// 바뀐페이지 정보로 요청
        }).done(function (resp) {
            // console.log(resp);
            resp = resp.response.body;// 받아온 정보 안의 response 안의 body
            console.log(resp);

            table = "";
            $(resp.items.item).each(function () {//데이터 뿌리기
                table += `<tr>
                           <td>\${this.yadmNm}</td>
                           <td>\${this.telno ? this.telno : "-"}</td>
                            <td class="text-start">\${this.addr}</td>
                          </tr>`;
            });
            // $("#data-list .pharmacy").append(table);
            $("#data-list .pharmacy tbody").html(table);
            makePage(resp.totalCount, curPage);
        })
    }

    var page = {pageList: 10, blockPage: 10};



    //유기 동물 목록 조회
    function animal_list() {

    }
</script>
</body>
</html>