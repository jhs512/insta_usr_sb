package com.sbs.untactTeacher.config;


import com.sbs.untactTeacher.interceptor.BeforeActionInterceptor;
import com.sbs.untactTeacher.interceptor.NeedAdminInterceptor;
import com.sbs.untactTeacher.interceptor.NeedToLoginInterceptor;
import com.sbs.untactTeacher.interceptor.NeedToLogoutInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    // beforeActionInterceptor 인터셉터 불러오기
    @Autowired
    BeforeActionInterceptor beforeActionInterceptor;

    @Autowired
    NeedToLoginInterceptor needToLoginInterceptor;

    @Autowired
    NeedAdminInterceptor needAdminInterceptor;

    @Autowired
    NeedToLogoutInterceptor needToLogoutInterceptor;

    @Value("${custom.genFileDirPath}")
    private String genFileDirPath;

    // 이 함수는 인터셉터를 적용하는 역할을 합니다.
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // beforeActionInterceptor 인터셉터가 모든 액션 실행전에 실행되도록 처리
        registry.addInterceptor(beforeActionInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/resource/**")
                .excludePathPatterns("/error");

        registry.addInterceptor(needToLoginInterceptor)
                .addPathPatterns("/mpaUsr/article/write")
                .addPathPatterns("/mpaUsr/article/doWrite")
                .addPathPatterns("/mpaUsr/article/doDelete")
                .addPathPatterns("/mpaUsr/article/modify")
                .addPathPatterns("/mpaUsr/article/doModify")
                .addPathPatterns("/mpaUsr/reply/doWrite")
                .addPathPatterns("/mpaUsr/reply/doDelete")
                .addPathPatterns("/mpaUsr/reply/doDeleteAjax")
                .addPathPatterns("/mpaUsr/reply/modify")
                .addPathPatterns("/mpaUsr/reply/doModify")
                .addPathPatterns("/mpaUsr/member/modify")
                .addPathPatterns("/mpaUsr/member/doModify")
                .addPathPatterns("/mpaUsr/member/checkPassword")
                .addPathPatterns("/mpaUsr/member/doCheckPassword");

        registry.addInterceptor(needToLogoutInterceptor)
                .addPathPatterns("/mpaUsr/member/findLoginId")
                .addPathPatterns("/mpaUsr/member/doFindLoginId")
                .addPathPatterns("/mpaUsr/member/findLoginPw")
                .addPathPatterns("/mpaUsr/member/doFindLoginPw")
                .addPathPatterns("/mpaUsr/member/login")
                .addPathPatterns("/mpaUsr/member/doLogin")
                .addPathPatterns("/mpaUsr/member/getLoginIdDup")
                .addPathPatterns("/mpaUsr/member/join")
                .addPathPatterns("/mpaUsr/member/doJoin")
                .addPathPatterns("/mpaUsr/member/findLoginId")
                .addPathPatterns("/mpaUsr/member/doFindLoginId")
                .addPathPatterns("/mpaUsr/member/findLoginPw")
                .addPathPatterns("/mpaUsr/member/doFindLoginPw");

        // /mpaAdm/** 으로 시작하는 모든 URI에 대해서 관리자인지 체크
        // 단 일부 URI는 체크를 안함
        registry.addInterceptor(needAdminInterceptor)
                .addPathPatterns("/mpaAdm/**")
                .excludePathPatterns("/mpaAdm/member/findLoginId")
                .excludePathPatterns("/mpaAdm/member/doFindLoginId")
                .excludePathPatterns("/mpaAdm/member/findLoginPw")
                .excludePathPatterns("/mpaAdm/member/doFindLoginPw")
                .excludePathPatterns("/mpaAdm/member/login")
                .excludePathPatterns("/mpaAdm/member/doLogin")
                .excludePathPatterns("/mpaAdm/member/getLoginIdDup")
                .excludePathPatterns("/mpaAdm/member/join")
                .excludePathPatterns("/mpaAdm/member/doJoin")
                .excludePathPatterns("/mpaAdm/member/findLoginId")
                .excludePathPatterns("/mpaAdm/member/doFindLoginId")
                .excludePathPatterns("/mpaAdm/member/findLoginPw")
                .excludePathPatterns("/mpaAdm/member/doFindLoginPw");

        registry.addInterceptor(needToLogoutInterceptor)
                .addPathPatterns("/mpaAdm/member/findLoginId")
                .addPathPatterns("/mpaAdm/member/doFindLoginId")
                .addPathPatterns("/mpaAdm/member/findLoginPw")
                .addPathPatterns("/mpaAdm/member/doFindLoginPw")
                .addPathPatterns("/mpaAdm/member/login")
                .addPathPatterns("/mpaAdm/member/doLogin")
                .addPathPatterns("/mpaAdm/member/getLoginIdDup")
                .addPathPatterns("/mpaAdm/member/join")
                .addPathPatterns("/mpaAdm/member/doJoin")
                .addPathPatterns("/mpaAdm/member/findLoginId")
                .addPathPatterns("/mpaAdm/member/doFindLoginId")
                .addPathPatterns("/mpaAdm/member/findLoginPw")
                .addPathPatterns("/mpaAdm/member/doFindLoginPw");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/gen/**").addResourceLocations("file:///" + genFileDirPath + "/")
                .setCachePeriod(20);
    }
}

