<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle"
	value="<span><i class='far fa-clipboard'></i></span> <span>${board.name} ARTICLE DETAIL</span>" />

<%@ include file="../common/head.jspf"%>

<div class="section section-article-detail">
	<div class="container mx-auto">
	    <div class="card bordered shadow-lg item-bt-1-not-last-child">
            <div class="card-title">
                <a href="javascript:history.back();" class="cursor-pointer">
                    <i class="fas fa-chevron-left"></i>
                </a>
                <span>게시물 상세페이지</span>
            </div>
            <div>
                <h1 class="title-bar-type-2 px-4">상세내용</h1>
                <div class="px-4 py-8">
                    <div class="flex">
                        <span>
                            <span>Comments:</span>
                            <span class="text-gray-400 text-light">30</span>
                        </span>
                        <span class="ml-3">
                            <span>Views:</span>
                            <span class="text-gray-400 text-light">60k</span>
                        </span>
                        <div class="flex-grow"></div>
                        <span>
                            <span>Likes:</span>
                            <span class="text-gray-400 text-light">120k</span>
                        </span>
                    </div>

                    <div class="mt-4">
                        <span class="badge badge-outline">제목</span>
                        <div>
                            ${article.title}
                        </div>
                    </div>

                    <div class="mt-3">
                        <span class="badge badge-accent">작성자</span>
                        <div class="mt-2">
                            <img class="w-40 h-40 object-cover rounded" onerror="${article.writerProfileFallbackImgOnErrorHtmlAttr}" src="${article.writerProfileImgUri}" alt="">
                            <span>${article.extra__writerName}</span>
                        </div>
                    </div>

                    <div class="mt-3 grid sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-3">
                        <div>
                            <span class="badge badge-primary">번호</span>
                            <span>${article.id}</span>
                        </div>

                        <div>
                            <span class="badge">등록날짜</span>
                            <span class="text-gray-600 text-light">${article.regDate}</span>
                        </div>

                        <div>
                            <span class="badge">수정날짜</span>
                            <span class="text-gray-600 text-light">${article.updateDate}</span>
                        </div>
                    </div>

                    <div class="mt-6">
                        <span class="badge badge-outline">본문</span>
                        <div class="mt-3">
                            <img class="rounded" src="https://i.pravatar.cc/250?img=37" alt="">
                        </div>
                        <div class="mt-3">
                            ${article.bodyForPrint}
                        </div>
                    </div>
                </div>
            </div>

            <div>
                <h1 class="title-bar-type-2 px-4">댓글</h1>

                <c:if test="${rq.notLogined}">
                    <div class="text-center py-4">
                        글 작성은 <a class="plain-link" href="${rq.loginPageUri}">로그인</a> 후 이용할 수 있습니다.
                    </div>
                </c:if>
                <c:if test="${rq.logined}">
                    <div class="px-4 py-8">
                        <!-- 댓글 입력 시작 -->
                        <script>
                        let ReplyWrite__submitFormDone = false;
                        function ReplyWrite__submitForm(form) {
                            if ( ReplyWrite__submitFormDone ) {
                                return;
                            }

                            form.body.value = form.body.value.trim();

                            if ( form.body.value.length == 0 ) {
                                alert('내용을 입력해주세요.');
                                form.body.focus();

                                return;
                            }

                            form.submit();
                            ReplyWrite__submitFormDone = true;
                        }
                        </script>
                        <form method="POST" action="../reply/doWrite" class="relative flex py-4 text-gray-600 focus-within:text-gray-400" onsubmit="ReplyWrite__submitForm(this); return false;">
                            <input type="hidden" name="relTypeCode" value="article" />
                            <input type="hidden" name="relId" value="${article.id}" />
                            <input type="hidden" name="redirectUri" value="${rq.currentUri}" />
                            <img class="w-10 h-10 object-cover rounded-full shadow mr-2 cursor-pointer" alt="User avatar" src="https://images.unsplash.com/photo-1477118476589-bff2c5c4cfbb?ixlib=rb-1.2.1&amp;ixid=eyJhcHBfaWQiOjEyMDd9&amp;auto=format&amp;fit=crop&amp;w=200&amp;q=200">

                            <span class="absolute inset-y-0 right-0 flex items-center pr-6">
                                <button type="submit" class="p-1 focus:outline-none focus:shadow-none hover:text-blue-500">
                                    <i class="fas fa-pen"></i>
                                </button>
                            </span>

                            <input name="body" type="text" class="w-full py-2 pl-4 pr-10 text-sm bg-gray-100 border border-transparent appearance-none rounded-tg placeholder-gray-400 focus:bg-white focus:outline-none focus:border-blue-500 focus:text-gray-900 focus:shadow-outline-blue" style="border-radius: 25px" placeholder="댓글을 입력해주세요." autocomplete="off">
                        </form>
                        <!-- 댓글 입력 끝 -->
                    </div>
                </c:if>

                <!-- 댓글 리스트 -->
                <style>
                .reply-list [data-id] {
                  transition: background-color 1s;
                }

                .reply-list [data-id].focus {
                  background-color:#efefef;
                  transition: background-color 0s;
                }
                </style>

                <script>
                function ReplyList__goToReply(id) {
                    setTimeout(function() {
                        const $target = $('.reply-list [data-id="' + id + '"]');
                        const targetOffset = $target.offset();
                        $(window).scrollTop(targetOffset.top - 50);
                        $target.addClass('focus');

                        setTimeout(function() {
                            $target.removeClass('focus');
                        }, 1000);

                    }, 1000);
                }

                function ReplyList__deleteReply(btn) {
                    const $clicked = $(btn);
                    const $target = $clicked.closest('[data-id]');
                    const id = $target.attr('data-id');

                    $clicked.text('삭제중...');

                    $.post(
                        '../reply/doDeleteAjax',
                        {
                            id: id
                        },
                        function(data) {
                            if ( data.success ) {
                                $target.remove();
                            }
                            else {
                                if ( data.msg ) {
                                    alert(data.msg);
                                }
                                $clicked.text('삭제실패!!');
                            }
                        },
                        'json'
                    );

                }

                if ( param.focusReplyId ) {
                    ReplyList__goToReply(param.focusReplyId);
                }
                </script>

                <div class="reply-list">
                    <c:forEach items="${replies}" var="reply">
                        <div data-id="${reply.id}" class="py-5 px-4">
                            <div class="flex">
                                <!-- 아바타 이미지 -->
                                <div class="flex-shrink-0">
                                    <img class="w-10 h-10 object-cover rounded-full shadow mr-2 cursor-pointer" alt="User avatar" src="https://images.unsplash.com/photo-1477118476589-bff2c5c4cfbb?ixlib=rb-1.2.1&amp;ixid=eyJhcHBfaWQiOjEyMDd9&amp;auto=format&amp;fit=crop&amp;w=200&amp;q=200">
                                </div>

                                <div class="flex-grow px-1">
                                    <div class="flex text-gray-400 text-light text-sm">
                                        <spqn>${reply.extra__writerName}</spqn>
                                        <span class="mx-1">·</span>
                                        <spqn>${reply.updateDate}</spqn>
                                    </div>
                                    <div class="break-all">
                                        ${reply.bodyForPrint}
                                    </div>
                                    <div class="mt-1">
                                        <span class="text-gray-400 cursor-pointer">
                                            <span><i class="fas fa-thumbs-up"></i></span>
                                            <span>5,600</span>
                                        </span>
                                        <span class="ml-1 text-gray-400 cursor-pointer">
                                            <span><i class="fas fa-thumbs-down"></i></span>
                                            <span>5,600</span>
                                        </span>
                                    </div>
                                </div>
                            </div>
                            <div class="plain-link-wrap gap-3 mt-3 pl-14">
                                <c:if test="${reply.memberId == rq.loginedMemberId}">
                                    <a onclick="if ( confirm('정말 삭제하시겠습니까?') ) { ReplyList__deleteReply(this); } return false;" class="plain-link">
                                        <span><i class="fas fa-trash-alt"></i></span>
                                        <span>글 삭제</span>
                                    </a>
                                </c:if>
                                <c:if test="${reply.memberId == rq.loginedMemberId}">
                                    <a href="../reply/modify?id=${reply.id}&redirectUri=${rq.encodedCurrentUri}" class="plain-link">
                                        <span><i class="far fa-edit"></i></span>
                                        <span>글 수정</span>
                                    </a>
                                </c:if>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
	</div>
</div>

<%@ include file="../common/foot.jspf"%>