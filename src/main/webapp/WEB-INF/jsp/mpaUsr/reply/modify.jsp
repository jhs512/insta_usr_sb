<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle"
	value="<span><i class='far fa-clipboard'></i></span> <span>${board.name} REPLY MODIFY</span>" />

<%@ include file="../common/head.jspf"%>

<script>
let ReplyModify__submitFormDone = false;
function ReplyModify__submitForm(form) {
    if ( ReplyModify__submitFormDone ) {
        return;
    }

    form.body.value = form.body.value.trim();

    if ( form.body.value.length == 0 ) {
        alert('내용을 입력해주세요.');
        form.body.focus();

        return;
    }

    form.submit();
    ReplyModify__submitFormDone = true;
}
</script>

<div class="section section-article-write">
	<div class="container mx-auto">
	    <form method="POST" action="doModify" onsubmit="ReplyModify__submitForm(this); return false;">
	        <input type="hidden" name="id" value="${reply.id}" />
	        <input type="hidden" name="redirectUri" value="${param.redirectUri}" />

	        <div class="form-control">
                <label class="label">
                    게시글 번호
                </label>
                <div>
                    ${reply.relId}
                </div>
            </div>

            <div class="form-control">
                <label class="label">
                    게시글 제목
                </label>
                <div>
                    ${title}
                </div>
            </div>

	        <div class="form-control">
                <label class="label">
                    내용
                </label>
                <textarea class="textarea textarea-bordered w-full h-24" placeholder="내용을 입력해주세요." name="body" maxlength="2000">${reply.body}</textarea>
            </div>

            <div class="mt-4 btn-wrap gap-1">
                <button type="submit" href="#" class="btn btn-primary btn-sm mb-1">
                    <span><i class="far fa-edit"></i></span>
                    &nbsp;
                    <span>수정</span>
                </button>

                <a href="#" onclick="history.back();" class="btn btn-sm mb-1" title="자세히 보기">
                    <span><i class="fas fa-list"></i></span>
                    &nbsp;
                    <span>리스트</span>
                </a>
            </div>
	    </form>
	</div>
</div>

<%@ include file="../common/foot.jspf"%>