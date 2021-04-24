package com.sbs.untactTeacher.interceptor;

import com.sbs.untactTeacher.dto.Rq;
import com.sbs.untactTeacher.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class NeedToLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        Rq rq = (Rq) req.getAttribute("rq");

        if (rq.isNotLogined()) {
            resp.setContentType("text/html; charset=UTF-8");
            String afterLoginUrl = "../article/write?boardId=1";
            String urlEncodedAfterLoginUrl = Util.getUrlEncoded(afterLoginUrl);
            resp.getWriter().append(Util.msgAndReplace("로그인 후 이용해주세요.", "../member/login?afterLoginUrl=" + urlEncodedAfterLoginUrl));
            return false;
        }

        return HandlerInterceptor.super.preHandle(req, resp, handler);
    }
}
