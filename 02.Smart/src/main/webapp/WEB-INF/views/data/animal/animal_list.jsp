<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>
<style>
    .popfile {width: 120px; height: 120px;}
</style>

<c:forEach items="${list.response.body.items.item}" var="vo">


<table class="table tb-list animal">
    <colgroup>
    <col width="120px">
    <col width="120px"><col width="60px">
    <col width="70px"><col width="160px">
    <col width="70px"><col width="120px">
    <col width="70px"><col width="160px">
    <col width="120px"><col width="110px">
    </colgroup>
    <tr>
        <td rowspan="3"><img class="popfile" src="${vo.popfile}">
        </td>
        <th>성별</th><td>${vo.sexCd}</td>
        <th>나이</th><td>${vo.age}</td>
        <th>체중</th><td>${vo.weight}</td>
        <th>색상</th><td>${vo.colorCd}</td>
        <th>접수 일자</th><td>${vo.happenDt}</td>
    </tr>
    <tr>
        <th>특징</th><td colspan="9" class="text-start">${vo.specialMark}</td>
    </tr>
    <tr>
        <th>발견 장소</th><td colspan="7" class="text-start">${vo.happenPlace}</td>
        <td colspan="2">${vo.processState}</td>
    </tr>
    <tr>
        <td colspan="2">${vo.careNm}</td>
        <td colspan="7" class="text-start">${vo.careAddr}</td>
        <td colspan="2">${vo.careTel}</td>
    </tr>
</table>
</c:forEach>
<script>
    $(".popfile").click(function () {
        $("#modal-map .modal-body").html($(this).clone());
        $("#modal-map img").removeClass("popfile");
        new bootstrap.Modal($("#modal-map")).show();
    })

    makePage(${list.response.body.totalCount}, ${list.response.body.pageNo});
</script>
</body>
</html>