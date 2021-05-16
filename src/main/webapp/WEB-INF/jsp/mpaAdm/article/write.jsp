<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle"
	value="<span><i class='far fa-clipboard'></i></span> <span>${board.name} ARTICLE WRITE</span>" />

<%@ include file="../common/head.jspf"%>

<script>
let ArticleWrite__submitFormDone = false;
function ArticleWrite__submitForm(form) {
    if ( ArticleWrite__submitFormDone ) {
        return;
    }

    form.title.value = form.title.value.trim();

    if ( form.title.value.length == 0 ) {
        alert('제목을 입력해주세요.');
        form.title.focus();

        return;
    }

    form.body.value = form.body.value.trim();

    if ( form.body.value.length == 0 ) {
        alert('내용을 입력해주세요.');
        form.body.focus();

        return;
    }

    form.submit();
    ArticleWrite__submitFormDone = true;
}
</script>

<div class="section section-article-write">
	<div class="container mx-auto">
	    <form method="POST" action="doWrite" onsubmit="ArticleWrite__submitForm(this); return false;">
	        <input type="hidden" name="boardId" value="${board.id}" />
	        <div class="form-control">
                <label class="label">
                    제목
                </label>
                <input class="input input-bordered w-full" type="text" maxlength="100" name="title" placeholder="제목을 입력해주세요." />
            </div>

            <div class="form-control">
                <label class="label">
                    내용
                </label>
                <textarea class="textarea textarea-bordered w-full h-24" placeholder="내용을 입력해주세요." name="body" maxlength="2000"></textarea>
            </div>

            <div class="mt-4 btn-wrap gap-1">
                <button type="submit" href="#" class="btn btn-primary btn-sm mb-1">
                    <span><i class="fas fa-save"></i></span>
                    &nbsp;
                    <span>작성</span>
                </button>

                <a href="#" class="btn btn-sm mb-1" title="자세히 보기">
                    <span><i class="fas fa-list"></i></span>
                    &nbsp;
                    <span>리스트</span>
                </a>
            </div>
	    </form>
	</div>
</div>

<%@ include file="../common/foot.jspf"%>