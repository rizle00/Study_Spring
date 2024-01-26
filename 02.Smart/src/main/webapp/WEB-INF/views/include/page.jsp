<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<nav aria-label="Page navigation example">
    <ul class="pagination mt-4 justify-content-center">
        <c:if test="${page.curBlock > 1}">
        <li class="page-item">
            <a class="page-link" onclick="go_page(1)" aria-label="First">
                <i class="fa-solid fa-backward"></i>
            </a>
        </li>
        <li class="page-item">
            <a class="page-link" onclick="go_page(${page.beginPage - page.blockPage})" aria-label="Previous">
                <i class="fa-solid fa-caret-left"></i>
            </a>
        </li>
        </c:if>
        <c:forEach var="no" begin="${page.beginPage}" end="${page.endPage}" step="1">
            <c:if test="${no eq page.curPage}">
                <li class="page-item"><a class="page-link active ">${no}</a> </li>
            </c:if>
            <c:if test="${no ne page.curPage}">
                <li class="page-item"><a class="page-link " onclick="go_page(${no})">${no}</a> </li>
            </c:if>
        </c:forEach>
        <c:if test="${page.curBlock < page.totalBlock}">
        <li class="page-item">
            <a class="page-link" aria-label="Next" onclick="go_page(${page.endPage+1})">
                <i class="fa-solid fa-caret-right"></i>
<%--                <span aria-hidden="true"></span>--%>
            </a>
        </li>
        <li class="page-item">
            <a class="page-link" onclick="go_page(${page.totalPage})" aria-label="Last">
                <i class="fa-solid fa-forward"></i>
                <%--                <span aria-hidden="true"></span>--%>
            </a>
        </li>
        </c:if>
    </ul>
</nav>
<script>
    function go_page( no ){
    $("[name=curPage]").val(no);
    $("form").submit();
    }
</script>