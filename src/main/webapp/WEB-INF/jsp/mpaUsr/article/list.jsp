<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="<span><i class='far fa-clipboard'></i></span> <span>${board.name} ARTICLE LIST</span>" />

<%@ include file="../common/head.jspf" %>

<div class="section section-article-list">
	<div class="container mx-auto">
		<span>TOTAL ITEMS : </span>
		<span>${totalItemsCount}</span>
	</div>
</div>

<%@ include file="../common/foot.jspf" %>