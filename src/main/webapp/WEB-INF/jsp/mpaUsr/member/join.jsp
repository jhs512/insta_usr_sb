<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle"
	value="<span><i class='fas fa-user-plus'></i></span> <span>MEMBER JOIN</span>" />

<%@ include file="../common/head.jspf"%>

<script src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>

<script>
let MemberJoin__validLoginId = "";
let MemberJoin__lastDupCheckedLoginId = "";
let MemberJoin__submitFormDone = false;
function MemberJoin__submitForm(form) {
    if ( MemberJoin__submitFormDone ) {
        return;
    }

    form.loginId.value = form.loginId.value.trim();

    if ( form.loginId.value.length == 0 ) {
        alert('로그인아이디를 입력해주세요.');
        form.loginId.focus();

        return;
    }

    if ( form.loginId.value.length <= 4 ) {
        alert('로그인아이디를 5자 이상으로 입력해주세요.');
        form.loginId.focus();

        return;
    }

    if ( MemberJoin__validLoginId != form.loginId.value ) {
        alert('해당 로그인아이디를 이용할 수 없습니다. 다른 로그인아이디를 입력해주세요.');
        form.loginId.focus();

        return;
    }

    form.loginPwInput.value = form.loginPwInput.value.trim();

    if ( form.loginPwInput.value.length == 0 ) {
        alert('로그인비밀번호을 입력해주세요.');
        form.loginPwInput.focus();

        return;
    }

    form.loginPwConfirm.value = form.loginPwConfirm.value.trim();

    if ( form.loginPwConfirm.value.length == 0 ) {
        alert('로그인비밀번호 확인을 입력해주세요.');
        form.loginPwConfirm.focus();

        return;
    }

    if ( form.loginPwInput.value != form.loginPwConfirm.value ) {
        alert('로그인비밀번호가 일치하지 않습니다.');
        form.loginPwConfirm.focus();

        return;
    }

    form.name.value = form.name.value.trim();

    if ( form.name.value.length == 0 ) {
        alert('이름을 입력해주세요.');
        form.name.focus();

        return;
    }

    form.nickname.value = form.nickname.value.trim();

    if ( form.nickname.value.length == 0 ) {
        alert('별명을 입력해주세요.');
        form.nickname.focus();

        return;
    }

    const maxSizeMb = 10;

    const maxSize = maxSizeMb * 1024 * 1024;

    const profileImgFileInput = form["file__member__0__extra__profileImg__1"];

    if (profileImgFileInput.value) {
        if (profileImgFileInput.files[0].size > maxSize) {
            alert(maxSizeMb + "MB 이하의 파일을 업로드 해주세요.");
            profileImgFileInput.focus();

            return;
        }
    }

    form.cellphoneNo.value = form.cellphoneNo.value.trim();

    if ( form.cellphoneNo.value.length == 0 ) {
        alert('휴대전화번호를 입력해주세요.');
        form.cellphoneNo.focus();

        return;
    }


    form.email.value = form.email.value.trim();

    if ( form.email.value.length == 0 ) {
        alert('이메일을 입력해주세요.');
        form.email.focus();

        return;
    }

    form.loginPw.value = sha256(form.loginPwInput.value);
    form.loginPwInput.value = '';
    form.loginPwConfirm.value = '';

    form.submit();
    MemberJoin__submitFormDone = true;
}

function MemberJoin__checkLoginIdDup(el) {
    const form = $(el).closest('form').get(0);

    form.loginId.value = form.loginId.value.trim();

    if ( form.loginId.value == MemberJoin__lastDupCheckedLoginId ) {
        return;
    }

    MemberJoin__validLoginId = "";
    $('.login-id-input-success-msg').text('');
    $('.login-id-input-success-msg').hide();

    $('.login-id-input-error-msg').text('');
    $('.login-id-input-error-msg').hide();

    if ( form.loginId.value.length > 4 ) {
        MemberJoin__lastDupCheckedLoginId = form.loginId.value;

        $.get(
            "../member/getLoginIdDup",
            {
                loginId: form.loginId.value
            },
            function(data) {
                if ( data.success ) {
                    MemberJoin__validLoginId = data.body.loginId;
                    $('.login-id-input-success-msg').text(data.msg);
                    $('.login-id-input-success-msg').show();
                }
                else {
                    $('.login-id-input-error-msg').text(data.msg);
                    $('.login-id-input-error-msg').show();
                }
            },
            "json"
        );
    }
}
</script>

<div class="section section-join px-2">
	<div class="container mx-auto">
	    <form method="POST" enctype="multipart/form-data" action="doJoin" onsubmit="MemberJoin__submitForm(this); return false;">
	        <input type="hidden" name="loginPw">
	        <div class="form-control">
                <label class="label">
                    로그인아이디
                </label>
                <input onkeyup="MemberJoin__checkLoginIdDup(this);" class="input input-bordered w-full" type="text" maxlength="30" name="loginId" placeholder="로그인아이디를 입력해주세요." />
                <div class="mt-2 text-red-500 login-id-input-error-msg"></div>
                <div class="mt-2 text-green-500 login-id-input-success-msg"></div>
            </div>

            <div class="form-control">
                <label class="label">
                    로그인비밀번호
                </label>
                <input class="input input-bordered w-full" type="password" maxlength="30" name="loginPwInput" placeholder="로그인비밀번호를 입력해주세요." />
            </div>

            <div class="form-control">
                <label class="label">
                    로그인비밀번호 확인
                </label>
                <input class="input input-bordered w-full" type="password" maxlength="30" name="loginPwConfirm" placeholder="로그인비밀번호 확인을 입력해주세요." />
            </div>

            <div class="form-control">
                <label class="label">
                    이름
                </label>
                <input class="input input-bordered w-full" type="text" maxlength="30" name="name" placeholder="이름을 입력해주세요." />
            </div>

            <div class="form-control">
                <label class="label">
                    별명
                </label>
                <input class="input input-bordered w-full" type="text" maxlength="30" name="nickname" placeholder="별명을 입력해주세요." />
            </div>

            <div class="form-control">
                <label class="label">
                    프로필 이미지
                </label>
                <input accept="image/gif, image/jpeg, image/png" type="file" name="file__member__0__extra__profileImg__1" placeholder="프로필 이미지를 선택해주세요." />
            </div>

            <div class="form-control">
                <label class="label">
                    휴대전화번호
                </label>
                <input class="input input-bordered w-full" type="tel" maxlength="30" name="cellphoneNo" placeholder="휴대전화번호를 입력해주세요." />
            </div>

            <div class="form-control">
                <label class="label">
                    이메일
                </label>
                <input class="input input-bordered w-full" type="email" maxlength="50" name="email" placeholder="이메일을 입력해주세요." />
            </div>

            <div class="mt-4 btn-wrap gap-1">
                <button type="submit" href="#" class="btn btn-primary btn-sm mb-1">
                    <span><i class="fas fa-user-plus"></i></span>
                    &nbsp;
                    <span>가입</span>
                </button>

                <a href="#" class="btn btn-sm mb-1">
                    <span><i class="fas fa-home"></i></span>
                    &nbsp;
                    <span>홈</span>
                </a>
            </div>
	    </form>
	</div>
</div>

<%@ include file="../common/foot.jspf"%>