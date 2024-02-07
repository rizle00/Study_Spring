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
<c:set var="response" value="${list.response.body}"/>
<c:if test="${empty response.items.item}">
<table class="table tb-list empty">
    <tr><th></th></tr>
    <tr><td>${ empty list.response.header.errorMsg ? "동물 정보가 없습니다" : list.response.header.errorMsg}</td></tr>
</table>
</c:if>
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
    if(${empty list.response.body.items.item}){
        var path = "";
        $(".animal-top select").each(function () {
            if($(this).val() != ""){
               var item = $(this).find("option:selected").text();
              path += `<span class="me-2">\${item}</span>`;
            }
        });
        $("table.empty th").html(path);
    }
    $(".popfile").click(function () {
        $("#modal-map .modal-body").html($(this).clone());
        $("#modal-map img").removeClass("popfile").addClass("w-pct100");
        new bootstrap.Modal($("#modal-map")).show();
    })

    makePage(${ empty list.response.header.errorMsg ? list.response.body.totalCount : 0}, ${list.response.body.pageNo});
</script>
</body>
</html>