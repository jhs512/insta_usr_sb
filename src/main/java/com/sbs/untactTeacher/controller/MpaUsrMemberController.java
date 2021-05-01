package com.sbs.untactTeacher.controller;

import com.sbs.untactTeacher.dto.Member;
import com.sbs.untactTeacher.dto.ResultData;
import com.sbs.untactTeacher.dto.Rq;
import com.sbs.untactTeacher.service.MemberService;
import com.sbs.untactTeacher.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class MpaUsrMemberController {
    @Autowired
    private MemberService memberService;

    // checkPasswordAuthCode : 체크비밀번호인증코드
    @RequestMapping("/mpaUsr/member/modify")
    public String showModify(HttpServletRequest req,  String modifyPrivateAuthCode) {

        Member loginedMember = ((Rq) req.getAttribute("rq")).getLoginedMember();
        ResultData checkValidModifyPrivateAuthCodeResultData = memberService
                .checkValidModifyPrivateAuthCode(loginedMember.getId(), modifyPrivateAuthCode);

        if ( checkValidModifyPrivateAuthCodeResultData.isFail() ) {
            return Util.msgAndBack(req, checkValidModifyPrivateAuthCodeResultData.getMsg());
        }

        log.debug("checkValidModifyPrivateAuthCodeResultData : " + checkValidModifyPrivateAuthCodeResultData);

        return "mpaUsr/member/modify";
    }

    @RequestMapping("/mpaUsr/member/doModify")
    public String doModify(HttpServletRequest req, String loginPw, String name, String
            nickname, String cellphoneNo, String email) {

        if (loginPw != null && loginPw.trim().length() == 0) {
            loginPw = null;
        }

        int id = ((Rq) req.getAttribute("rq")).getLoginedMemberId();
        ResultData modifyRd = memberService.modify(id, loginPw, name, nickname, cellphoneNo, email);

        if (modifyRd.isFail()) {
            return Util.msgAndBack(req, modifyRd.getMsg());
        }

        return Util.msgAndReplace(req, modifyRd.getMsg(), "/");
    }

    @RequestMapping("/mpaUsr/member/mypage")
    public String showMypage(HttpServletRequest req) {
        return "mpaUsr/member/mypage";
    }

    @RequestMapping("/mpaUsr/member/login")
    public String showLogin(HttpServletRequest req) {
        return "mpaUsr/member/login";
    }

    @RequestMapping("/mpaUsr/member/findLoginPw")
    public String showFindLoginPw(HttpServletRequest req) {
        return "mpaUsr/member/findLoginPw";
    }

    @RequestMapping("/mpaUsr/member/doFindLoginPw")
    public String doFindLoginPw(HttpServletRequest req, String loginId, String name, String email, String redirectUri) {
        if (Util.isEmpty(redirectUri)) {
            redirectUri = "/";
        }

        Member member = memberService.getMemberByLoginId(loginId);

        if (member == null) {
            return Util.msgAndBack(req, "일치하는 회원이 존재하지 않습니다.");
        }

        if (member.getName().equals(name) == false) {
            return Util.msgAndBack(req, "일치하는 회원이 존재하지 않습니다.");
        }

        if (member.getEmail().equals(email) == false) {
            return Util.msgAndBack(req, "일치하는 회원이 존재하지 않습니다.");
        }

        ResultData notifyTempLoginPwByEmailRs = memberService.notifyTempLoginPwByEmail(member);

        return Util.msgAndReplace(req, notifyTempLoginPwByEmailRs.getMsg(), redirectUri);
    }

    @RequestMapping("/mpaUsr/member/findLoginId")
    public String showFindLoginId(HttpServletRequest req) {
        return "mpaUsr/member/findLoginId";
    }

    @RequestMapping("/mpaUsr/member/doFindLoginId")
    public String doFindLoginId(HttpServletRequest req, String name, String email, String redirectUri) {
        if (Util.isEmpty(redirectUri)) {
            redirectUri = "/";
        }

        Member member = memberService.getMemberByNameAndEmail(name, email);

        if (member == null) {
            return Util.msgAndBack(req, "일치하는 회원이 존재하지 않습니다.");
        }

        return Util.msgAndBack(req, String.format("회원님의 아이디는 `%s` 입니다.", member.getLoginId()));
    }

    @RequestMapping("/mpaUsr/member/doLogout")
    public String doLogout(HttpServletRequest req, HttpSession session) {
        session.removeAttribute("loginedMemberId");

        String msg = "로그아웃 되었습니다.";
        return Util.msgAndReplace(req, msg, "/");
    }

    @RequestMapping("/mpaUsr/member/doLogin")
    public String doLogin(HttpServletRequest req, HttpSession session, String loginId, String loginPw, String
            redirectUri) {
        if (Util.isEmpty(redirectUri)) {
            redirectUri = "/";
        }

        Member member = memberService.getMemberByLoginId(loginId);

        if (member == null) {
            return Util.msgAndBack(req, loginId + "(은)는 존재하지 않는 로그인아이디 입니다.");
        }

        if (member.getLoginPw().equals(loginPw) == false) {
            return Util.msgAndBack(req, "비밀번호가 일치하지 않습니다.");
        }

        //HttpSession session = req.getSession();
        session.setAttribute("loginedMemberId", member.getId());

        String msg = "환영합니다.";
        return Util.msgAndReplace(req, msg, redirectUri);
    }

    @RequestMapping("/mpaUsr/member/join")
    public String showJoin(HttpServletRequest req) {
        return "mpaUsr/member/join";
    }

    @RequestMapping("/mpaUsr/member/doJoin")
    public String doJoin(HttpServletRequest req, String loginId, String loginPw, String name, String
            nickname, String cellphoneNo, String email) {
        Member oldMember = memberService.getMemberByLoginId(loginId);

        if (oldMember != null) {
            return Util.msgAndBack(req, loginId + "(은)는 이미 사용중인 로그인아이디 입니다.");
        }

        ResultData joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNo, email);

        if (joinRd.isFail()) {
            return Util.msgAndBack(req, joinRd.getMsg());
        }

        return Util.msgAndReplace(req, joinRd.getMsg(), "/");
    }

    @RequestMapping("/mpaUsr/member/checkPassword")
    public String showCheckPassword(HttpServletRequest req) {
        return "mpaUsr/member/checkPassword";
    }

    @RequestMapping("/mpaUsr/member/doCheckPassword")
    public String doCheckPassword(HttpServletRequest req, String loginPw, String redirectUri) {
        Member loginedMember = ((Rq) req.getAttribute("rq")).getLoginedMember();

        if (loginedMember.getLoginPw().equals(loginPw) == false) {
            return Util.msgAndBack(req, "비밀번호가 일치하지 않습니다.");
        }

        return Util.msgAndReplace(req, "", redirectUri);
    }
}
