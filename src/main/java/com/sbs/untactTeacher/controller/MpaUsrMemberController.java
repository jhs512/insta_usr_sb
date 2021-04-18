package com.sbs.untactTeacher.controller;

import com.sbs.untactTeacher.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@Slf4j
public class MpaUsrMemberController {
    @RequestMapping("/mpaUsr/member/join")
    public String showJoin(HttpServletRequest req) {
        return "mpaUsr/member/join";
    }

    @RequestMapping("/mpaUsr/member/doJoin")
    @ResponseBody
    public Map doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNo, String email) {
        return Util.mapOf("loginId", loginId, "loginPw", loginPw, "name", name, "nickname", nickname, "cellphoneNo", cellphoneNo, "email", email);
    }
}
