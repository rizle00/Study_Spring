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
        <a class="nav-link active" aria-current="page">약국조회</a>
    </li>
    <li class="nav-item">
        <a class="nav-link">유기동물조회</a>
    </li>
    <li class="nav-item">
        <a class="nav-link">기타조회</a>
    </li>
    <li class="nav-item">
        <a class="nav-link">기타조회</a>
    </li>
</ul>
<div class="row mb-2 justify-content-sm-between mb-1">

    <div class="col-auto d-flex gap-2 animal-top">
    </div>
    <div class="col-auto">
        <select id="pageList" class="form-select">
            <c:forEach var="i" begin="1" end="5">
                <option value="${10*i}">${10*i}개 씩</option>
            </c:forEach>
        </select>
    </div>
</div>

<div id="data-list">

</div>
<jsp:include page="/WEB-INF/views/include/modal.jsp"/>
<jsp:include page="/WEB-INF/views/include/loading.jsp"/>
<div id="map" style="width:668px;height:700px;"></div>
<script src="<c:url value='/js/animal.js'/> "></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=fd560cb2c1d566b1339c4e0f4073ddf4"></script>
<script>
    $("ul.nav-pills li").click(function () {
        $("ul.nav-pills li a").removeClass("active");
        $(this).children("a").addClass("active");// 클릭된 자기자신
        var index = $(this).index();
        if (index == 0) {
            pharmacy_list(1);
        } else if (index == 1) {
            animal_list(1);
        }
    })
    $(function () {// 다큐먼트 레디..
        $("ul.nav-pills li").eq(0).trigger("click"); //클릭 이벤트 강제 발생
    })

    $(document)
        .on("click", ".pagination li a", function () {
            if ($("table.pharmacy").length > 0) {

                pharmacy_list($(this).data("page"));// 현재페이지 바뀜처리
            } else
                animal_list($(this).data("page"))
        })
        .on("click", ".map", function () {
            if ($(this).data("x") != "undefined" && $(this).data("y") != "undefined") {

                showKakaoMap($(this));
            } else {
                alert("위치 정보가 없어 지도에 표시할 수 없습니다")
            }
        })

    function showKakaoMap(tag) {
        $("#map").remove();
        $("#modal-map").after(`<div id="map" style="width:668px;height:700px;"></div>`);
        var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
        var xy = new kakao.maps.LatLng(tag.data("y"), tag.data("x"));
        var options = { //지도를 생성할 때 필요한 기본 옵션
            center: xy, //지도의 중심좌표.
            level: 3 //지도의 레벨(확대, 축소 정도)
        };

        var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴


// 마커를 생성합니다
        var marker = new kakao.maps.Marker({
            position: xy
        });

// 마커가 지도 위에 표시되도록 설정합니다
        marker.setMap(map);

        var iwContent = '<div style="padding:5px;">Hello World! <br><a href="https://map.kakao.com/link/map/Hello World!,33.450701,126.570667" style="color:blue" target="_blank">큰지도보기</a> <a href="https://map.kakao.com/link/to/Hello World!,33.450701,126.570667" style="color:blue" target="_blank">길찾기</a></div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
            iwPosition = xy; //인포윈도우 표시 위치입니다

// 인포윈도우를 생성합니다
        var name = tag.text();
        var infowindow = new kakao.maps.InfoWindow({
            position: iwPosition,
            content: `<div style="padding:5px;" class="text-danger fw-bold" >\${name}</div>`
        });

// 마커 위에 인포윈도우를 표시합니다. 두번째 파라미터인 marker를 넣어주지 않으면 지도 위에 표시됩니다
        infowindow.open(map, marker);
        $("#modal-map .modal-body").html($("#map"));
        new bootstrap.Modal($("#modal-map")).show();
    }

    $("#pageList").change(function () {
        page.pageList = $(this).val();

        if ($("table.pharmacy").length > 0) {

            pharmacy_list(1);// 페이지 갯수 바뀔때 초기화
        } else animal_list(1);
    })
    // pharmacy_list();
    //약국 목록 조회
    function pharmacy_list(curPage) {
        $(".animal-top").empty();
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
            data: {pageNo: curPage, numOfRows: page.pageList}// 바뀐페이지 정보로 요청
        }).done(function (resp) {
            // console.log(resp);
            resp = resp.response.body;// 받아온 정보 안의 response 안의 body
            console.log(resp);

            table = "";
            $(resp.items.item).each(function () {//데이터 뿌리기
                table += `<tr>
                           <td><a class="map text-link" data-x = "\${this.XPos}" data-y="\${this.YPos}">\${this.yadmNm}</td>
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
    function animal_list(curPage) {
        $(".loading").removeClass("d-none");


        if ($("#sido").length == 0) {
            // 시도 조회
            animal_sido();
        }

        var animal = {};
        animal.pageNo = curPage;
        animal.numOfRows = page.pageList;
        animal.sido = $("#sido").length == 1 ? $("#sido").val() : "";
        animal.sigungu = $("#sigungu").length == 1 ? $("#sigungu").val() : "";
        animal.shelter = $("#shelter").length == 1 ? $("#shelter").val() : "";
        animal.upkind = $("#upkind").length == 1 ? $("#upkind").val() : "";
        animal.kind = $("#kind").length == 1 ? $("#kind").val() : "";

        $.ajax({
            url: "animal/list",
            // data: {pageNo: curPage, numOfRows: page.pageList}
            data: JSON.stringify(animal),
            type: 'post',
            contentType: 'application/json'
        }).done(function (resp) {
            console.log(resp);
            $("#data-list").html(resp);
            setTimeout(function () {
                $(".loading").addClass("d-none");
            }, 500);
        });


    }


    function animal_sido() {
        $.ajax({
            url: "animal/sido",

        }).done(function (resp) {

            $(".animal-top").prepend(resp);
            animal_type();
        });

    }
</script>

</body>
</html>