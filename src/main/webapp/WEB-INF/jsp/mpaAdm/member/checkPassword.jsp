<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle"
	value="<span><i class='fas fa-sign-in-alt'></i></span> <span>CHECK PASSWORD</span>" />

<%@ include file="../common/head.jspf"%>

<script src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>

<script>
let MemberCheckPassword__submitFormDone = false;
function MemberCheckPassword__submitForm(form) {
    if ( MemberCheckPassword__submitFormDone ) {
        return;
    }

    form.loginPwInput.value = form.loginPwInput.value.trim();

    if ( form.loginPwInput.value.length == 0 ) {
        alert('로그인비밀번호을 입력해주세요.');
        form.loginPwInput.focus();

        return;
    }

    form.loginPw.value = sha256(form.loginPwInput.value);
    form.loginPwInput.value = '';

    form.submit();
    MemberCheckPassword__submitFormDone = true;
}
</script>

<div class="section section-login px-2">
	<div class="container mx-auto">
	    <form method="POST" action="doCheckPassword" onsubmit="MemberCheckPassword__submitForm(this); return false;">
	        <input type="hidden" name="redirectUri" value="${param.afterUri}" />
	        <input type="hidden" name="loginPw" />

	        <div class="form-control">
                <label class="label">
                    로그인비밀번호
                </label>
                <input class="input input-bordered w-full" type="password" maxlength="30" name="loginPwInput" placeholder="로그인비밀번호를 입력해주세요." />
            </div>

            <div class="mt-4 btn-wrap gap-1">
                <button type="submit" href="#" class="btn btn-primary btn-sm mb-1">
                    <span><i class="fas fa-sign-in-alt"></i></span>
                    &nbsp;
                    <span>비밀번호 확인</span>
                </button>

                <a href="#" class="btn btn-link btn-sm mb-1">
                    <span><i class="fas fa-home"></i></span>
                    &nbsp;
                    <span>홈</span>
                </a>
            </div>
	    </form>
	</div>
</div>

<%@ include file="../common/foot.jspf"%>