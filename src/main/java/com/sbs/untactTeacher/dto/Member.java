package com.sbs.untactTeacher.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sbs.untactTeacher.util.Util;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Member {
    private int id;
    private String regDate;
    private String updateDate;
    private String loginId;
    private String loginPw;
    private String name;
    private String nickname;
    private String cellphoneNo;
    private String email;
    private boolean delStatus;
    private String delDate;

    public String getAuthLevelName() {
        return "일반회원";
    }

    public String toJsonStr() {
        return Util.toJsonStr(this);
    }

    public String getProfileImgUri() {
        return "/common/genFile/file/member/" + id + "/extra/profileImg/1";
    }

    public String getProfileFallbackImgUri() {
        return "https://via.placeholder.com/300?text=^_^";
    }

    public String getProfileFallbackImgOnErrorHtmlAttr() {
        return "this.src = '" + getProfileFallbackImgUri() + "'";
    }
}