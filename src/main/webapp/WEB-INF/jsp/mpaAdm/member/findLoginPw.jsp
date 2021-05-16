<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle"
	value="<span><i class='fas fa-sign-in-alt'></i></span> <span>FIND LOGIN PW</span>" />

<%@ include file="../common/head.jspf"%>

<script>
let MemberFindLoginPw__submitFormDone = false;
function MemberFindLoginPw__submitForm(form) {
    if ( MemberFindLoginPw__submitFormDone ) {
        return;
    }

    form.loginId.value = form.loginId.value.trim();

    if ( form.loginId.value.length == 0 ) {
        alert('로그인아이디를 입력해주세요.');
        form.loginId.focus();

        return;
    }

    form.name.value = form.name.value.trim();

    if ( form.name.value.length == 0 ) {
        alert('이름을 입력해주세요.');
        form.name.focus();

        return;
    }

    form.email.value = form.email.value.trim();

    if ( form.email.value.length == 0 ) {
        alert('이메일을 입력해주세요.');
        form.email.focus();

        return;
    }

    form.submit();
    MemberFindLoginPw__submitFormDone = true;
}
</script>

<div class="section section-find-login-pw px-2">
	<div class="container mx-auto">
	    <form method="POST" action="doFindLoginPw" onsubmit="MemberFindLoginPw__submitForm(this); return false;">
	        <input type="hidden" name="redirectUri" value="${param.afterUri}" />
	        <div class="form-control">
                <label class="label">
                    로그인아이디
                </label>
                <input autofocus class="input input-bordered w-full" type="text" maxlength="30" name="loginId" placeholder="로그인아이디를 입력해주세요." />
            </div>

	        <div class="form-control">
                <label class="label">
                    이름
                </label>
                <input class="input input-bordered w-full" type="text" maxlength="30" name="name" placeholder="이름을 입력해주세요." />
            </div>

            <div class="form-control">
                <label class="label">
                    이메일
                </label>
                <input class="input input-bordered w-full" type="email" maxlength="100" name="email" placeholder="이메일을 입력해주세요." />
            </div>

            <div class="mt-4 btn-wrap gap-1">
                <button type="submit" href="#" class="btn btn-primary btn-sm mb-1">
                    <span><i class="fas fa-sign-in-alt"></i></span>
                    &nbsp;
                    <span>비빌번호 찾기</span>
                </button>

                <a href="../member/login" type="submit" href="#" class="btn btn-link btn-sm mb-1">
                    <span><i class="fas fa-sign-in-alt"></i></span>
                    &nbsp;
                    <span>로그인</span>
                </a>

                <a href="../member/join" type="submit" href="#" class="btn btn-link btn-sm mb-1">
                    <span><i class="fas fa-sign-in-alt"></i></span>
                    &nbsp;
                    <span>회원가입</span>
                </a>

                <a href="../member/findLoginId" type="submit" href="#" class="btn btn-link btn-sm mb-1">
                    <span><i class="fas fa-sign-in-alt"></i></span>
                    &nbsp;
                    <span>아이디 찾기</span>
                </a>

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