<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle"
	value="<span><i class='far fa-clipboard'></i></span> <span>${board.name} MEMBER LIST</span>" />

<%@ include file="../common/head.jspf"%>

<div class="section section-member-list">
	<div class="container mx-auto">
		<div>
			<div class="card bordered shadow-lg">
                <div class="card-title">
                    <a href="javascript:history.back();" class="cursor-pointer">
                        <i class="fas fa-chevron-left"></i>
                    </a>
                    <span>회원 리스트</span>
                </div>

                <div class="grid gap-3 px-4 pt-4">
                    <div class="total-items">
                        <span class="badge badge-primary">TOTAL ITEMS : </span>
                        <span>${members.size()}</span>
                    </div>
                </div>

                <div class="item-bt-1-not-last-child">
                    <c:forEach items="${members}" var="member">
                        <c:set var="detailUri" value="../member/detail?id=${member.id}" />
                        <!-- 회원 아이템, first -->
                        <div class="px-4 py-8">
                            <div class="grid gap-3" style="grid-template-columns: 100px 1fr;">
                                <a href="${detailUri}">
                                    <img class="w-full object-cover rounded-full" onerror="${member.profileFallbackImgOnErrorHtmlAttr}" src="${member.profileImgUri}" alt="">
                                </a>
                                <a href="${detailUri}" class="hover:underline">
                                    <span class="badge badge-primary">번호</span>
                                    <span>${member.id}</span>
                                </a>
                            </div>

                            <div class="mt-3 grid sm:grid-cols-2 xl:grid-cols-4 gap-3">
                                <a href="${detailUri}" class="cursor-pointer hover:underline">
                                    <span class="badge badge-accent">별명</span>
                                    <span>${member.nickname}</span>
                                </a>

                                <a href="${detailUri}" class="cursor-pointer hover:underline">
                                    <span class="badge badge-accent">이름</span>
                                    <span>${member.name}</span>
                                </a>

                                <a href="${detailUri}" class="hover:underline">
                                    <span class="badge">등록날짜</span>
                                    <span class="text-gray-600 text-light">${member.regDate}</span>
                                </a>

                                <a href="${detailUri}" class="hover:underline">
                                    <span class="badge">수정날짜</span>
                                    <span class="text-gray-600 text-light">${member.updateDate}</span>
                                </a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
		</div>
	</div>
</div>

<%@ include file="../common/foot.jspf"%>