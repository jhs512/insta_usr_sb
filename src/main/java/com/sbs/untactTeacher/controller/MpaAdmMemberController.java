package com.sbs.untactTeacher.controller;

import com.sbs.untactTeacher.dto.Member;
import com.sbs.untactTeacher.dto.ResultData;
import com.sbs.untactTeacher.dto.Rq;
import com.sbs.untactTeacher.service.GenFileService;
import com.sbs.untactTeacher.service.MemberService;
import com.sbs.untactTeacher.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class MpaAdmMemberController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private GenFileService genFileService;

    @RequestMapping("/mpaAdm/member/list")
    public String showList(HttpServletRequest req) {
        List<Member> members = memberService.getForPrintMembers();

        req.setAttribute("members", members);

        return "mpaAdm/member/list";
    }

    // checkPasswordAuthCode : 체크비밀번호인증코드
    @RequestMapping("/mpaAdm/member/modify")
    public String showModify(HttpServletRequest req, String checkPasswordAuthCode) {

        Member loginedMember = ((Rq) req.getAttribute("rq")).getLoginedMember();
        ResultData checkValidCheckPasswordAuthCodeResultData = memberService
                .checkValidCheckPasswordAuthCode(loginedMember.getId(), checkPasswordAuthCode);

        if (checkValidCheckPasswordAuthCodeResultData.isFail()) {
            return Util.msgAndBack(req, checkValidCheckPasswordAuthCodeResultData.getMsg());
        }

        return "mpaAdm/member/modify";
    }

    @RequestMapping("/mpaAdm/member/doModify")
    public String doModify(HttpServletRequest req, String loginPw, String name, String
            nickname, String cellphoneNo, String email, String checkPasswordAuthCode, MultipartRequest multipartRequest) {

        Member loginedMember = ((Rq) req.getAttribute("rq")).getLoginedMember();
        ResultData checkValidCheckPasswordAuthCodeResultData = memberService
                .checkValidCheckPasswordAuthCode(loginedMember.getId(), checkPasswordAuthCode);

        if (checkValidCheckPasswordAuthCodeResultData.isFail()) {
            return Util.msgAndBack(req, checkValidCheckPasswordAuthCodeResultData.getMsg());
        }

        if (loginPw != null && loginPw.trim().length() == 0) {
            loginPw = null;
        }

        int id = ((Rq) req.getAttribute("rq")).getLoginedMemberId();
        ResultData modifyRd = memberService.modify(id, loginPw, name, nickname, cellphoneNo, email);

        if (modifyRd.isFail()) {
            return Util.msgAndBack(req, modifyRd.getMsg());
        }

        if (req.getParameter("deleteFile__member__0__extra__profileImg__1") != null) {
            genFileService.deleteGenFile("member", loginedMember.getId(), "extra", "profileImg", 1);
        }

        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

        for (String fileInputName : fileMap.keySet()) {
            MultipartFile multipartFile = fileMap.get(fileInputName);

            if (multipartFile.isEmpty() == false) {
                genFileService.save(multipartFile, loginedMember.getId());
            }
        }

        return Util.msgAndReplace(req, modifyRd.getMsg(), "/");
    }

    @RequestMapping("/mpaAdm/member/mypage")
    public String showMypage(HttpServletRequest req) {
        return "mpaAdm/member/mypage";
    }

    @RequestMapping("/mpaAdm/member/login")
    public String showLogin(HttpServletRequest req) {
        return "mpaAdm/member/login";
    }

    @RequestMapping("/mpaAdm/member/findLoginPw")
    public String showFindLoginPw(HttpServletRequest req) {
        return "mpaAdm/member/findLoginPw";
    }

    @RequestMapping("/mpaAdm/member/doFindLoginPw")
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

    @RequestMapping("/mpaAdm/member/findLoginId")
    public String showFindLoginId(HttpServletRequest req) {
        return "mpaAdm/member/findLoginId";
    }

    @RequestMapping("/mpaAdm/member/doFindLoginId")
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

    @RequestMapping("/mpaAdm/member/doLogout")
    public String doLogout(HttpServletRequest req, HttpSession session) {
        session.removeAttribute("loginedMemberId");

        String msg = "로그아웃 되었습니다.";
        return Util.msgAndReplace(req, msg, "/");
    }

    @RequestMapping("/mpaAdm/member/doLogin")
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

        if (memberService.isAdmin(member) == false) {
            return Util.msgAndBack(req, "관리자 회원으로 로그인 해주세요.");
        }

        session.setAttribute("loginedMemberId", member.getId());
        session.setAttribute("loginedMemberJsonStr", member.toJsonStr());

        String msg = "환영합니다.";

        boolean needToChangePassword = memberService.needToChangePassword(member.getId());

        if (needToChangePassword) {
            msg = "현재 비밀번호를 사용한지 " + memberService.getNeedToChangePasswordFreeDays() + "일이 지났습니다. 비밀번호를 변경해주세요.";
            redirectUri = "/mpaAdm/member/mypage";
        }

        boolean isUsingTempPassword = memberService.usingTempPassword(member.getId());

        if (isUsingTempPassword) {
            msg = "임시 비밀번호를 변경해주세요.";
            redirectUri = "/mpaAdm/member/mypage";
        }

        return Util.msgAndReplace(req, msg, redirectUri);
    }

    @RequestMapping("/mpaAdm/member/join")
    public String showJoin(HttpServletRequest req) {
        return "mpaAdm/member/join";
    }

    @RequestMapping("/mpaAdm/member/getLoginIdDup")
    @ResponseBody
    public ResultData getLoginIdDup(HttpServletRequest req, String loginId) {
        Member member = memberService.getMemberByLoginId(loginId);

        if (member != null) {
            return new ResultData("F-1", "해당 로그인아이디는 이미 사용중입니다.", "loginId", loginId);
        }

        return new ResultData("S-1", "사용가능한 로그인 아이디 입니다.", "loginId", loginId);
    }

    @RequestMapping("/mpaAdm/member/doJoin")
    public String doJoin(HttpServletRequest req, String loginId, String loginPw, String name, String
            nickname, String cellphoneNo, String email, MultipartRequest multipartRequest) {

        Member oldMember = memberService.getMemberByLoginId(loginId);

        if (oldMember != null) {
            return Util.msgAndBack(req, loginId + "(은)는 이미 사용중인 로그인아이디 입니다.");
        }

        oldMember = memberService.getMemberByNameAndEmail(name, email);

        if (oldMember != null) {
            return Util.msgAndBack(req, String.format("%s님은 이미 %s 메일주소로 %s 에 가입하셨습니다.", name, email, oldMember.getRegDate()));
        }

        ResultData joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNo, email);

        if (joinRd.isFail()) {
            return Util.msgAndBack(req, joinRd.getMsg());
        }

        int newMemberId = (int) joinRd.getBody().get("id");

        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

        for (String fileInputName : fileMap.keySet()) {
            MultipartFile multipartFile = fileMap.get(fileInputName);

            if (multipartFile.isEmpty() == false) {
                genFileService.save(multipartFile, newMemberId);
            }
        }

        return Util.msgAndReplace(req, joinRd.getMsg(), "/");
    }

    @RequestMapping("/mpaAdm/member/checkPassword")
    public String showCheckPassword(HttpServletRequest req) {
        return "mpaAdm/member/checkPassword";
    }

    @RequestMapping("/mpaAdm/member/doCheckPassword")
    public String doCheckPassword(HttpServletRequest req, String loginPw, String redirectUri) {
        Member loginedMember = ((Rq) req.getAttribute("rq")).getLoginedMember();

        if (loginedMember.getLoginPw().equals(loginPw) == false) {
            return Util.msgAndBack(req, "비밀번호가 일치하지 않습니다.");
        }

        String authCode = memberService.genCheckPasswordAuthCode(loginedMember.getId());

        redirectUri = Util.getNewUri(redirectUri, "checkPasswordAuthCode", authCode);

        return Util.msgAndReplace(req, "", redirectUri);
    }
}
