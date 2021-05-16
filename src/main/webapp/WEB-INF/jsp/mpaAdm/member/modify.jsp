<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle"
	value="<span><i class='fas fa-user-plus'></i></span> <span>MEMBER MODIFY</span>" />

<%@ include file="../common/head.jspf"%>

<script src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>

<script>
let MemberModify__submitFormDone = false;
function MemberModify__submitForm(form) {
    if ( MemberModify__submitFormDone ) {
        return;
    }

    form.loginPwInput.value = form.loginPwInput.value.trim();

    if ( form.loginPwInput.value.length > 0 ) {
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

    const deleteProfileImgFileInput = form["deleteFile__member__0__extra__profileImg__1"];

    if ( deleteProfileImgFileInput.checked ) {
        form["file__member__0__extra__profileImg__1"].value = '';
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

    if ( form.loginPwInput.value.length > 0 ) {
        form.loginPw.value = sha256(form.loginPwInput.value);
        form.loginPwInput.value = '';
        form.loginPwConfirm.value = '';
    }

    form.submit();
    MemberModify__submitFormDone = true;
}
</script>

<div class="section section-member-modify px-2">
	<div class="container mx-auto">
	    <form method="POST" enctype="multipart/form-data" action="doModify" onsubmit="MemberModify__submitForm(this); return false;">
	        <input type="hidden" name="checkPasswordAuthCode" value="${param.checkPasswordAuthCode}">
	        <input type="hidden" name="loginPw">
	        <div class="form-control">
                <label class="label">
                    로그인아이디
                </label>
                <div class="plain-text">
                  ${rq.loginedMember.loginId}
                </div>
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
                <input value="${rq.loginedMember.name}" class="input input-bordered w-full" type="text" maxlength="30" name="name" placeholder="이름을 입력해주세요." />
            </div>

            <div class="form-control">
                <label class="label">
                    별명
                </label>
                <input value="${rq.loginedMember.nickname}" class="input input-bordered w-full" type="text" maxlength="30" name="nickname" placeholder="별명을 입력해주세요." />
            </div>

            <div class="form-control">
                <label class="label">
                    프로필 이미지
                </label>
                <img class="w-40 h-40 mb-2 object-cover rounded-full" onerror="${rq.loginedMember.removeProfileImgIfNotExistsOnErrorHtmlAttr}" src="${rq.loginedMember.profileImgUri}" alt="">

                <div>
                    <label class="cursor-pointer label inline-flex">
                        <span class="label-text mr-2">이미지 삭제</span>
                        <div>
                            <input type="checkbox" name="deleteFile__member__0__extra__profileImg__1" class="checkbox" value="Y">
                            <span class="checkbox-mark"></span>
                        </div>
                    </label>
                </div>
                <input accept="image/gif, image/jpeg, image/png" type="file" name="file__member__0__extra__profileImg__1" placeholder="프로필 이미지를 선택해주세요." />
            </div>



            <div class="form-control">
                <label class="label">
                    휴대전화번호
                </label>
                <input value="${rq.loginedMember.cellphoneNo}" class="input input-bordered w-full" type="tel" maxlength="30" name="cellphoneNo" placeholder="휴대전화번호를 입력해주세요." />
            </div>

            <div class="form-control">
                <label class="label">
                    이메일
                </label>
                <input value="${rq.loginedMember.email}" class="input input-bordered w-full" type="email" maxlength="50" name="email" placeholder="이메일을 입력해주세요." />
            </div>

            <div class="mt-4 btn-wrap gap-1">
                <button type="submit" href="#" class="btn btn-primary btn-sm mb-1">
                    <span><i class="fas fa-user-plus"></i></span>
                    &nbsp;
                    <span>수정</span>
                </button>

                <a href="../member/mypage" class="btn btn-link btn-sm mb-1">
                    <span><i class="fas fa-home"></i></span>
                    &nbsp;
                    <span>마이페이지</span>
                </a>

                <a href="/" class="btn btn-link btn-sm mb-1">
                    <span><i class="fas fa-home"></i></span>
                    &nbsp;
                    <span>홈</span>
                </a>
            </div>
	    </form>
	</div>
</div>

<%@ include file="../common/foot.jspf"%>