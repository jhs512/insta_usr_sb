package com.sbs.untactTeacher.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Article {
    private int id;
    private String regDate;
    private String updateDate;
    private int boardId;
    private int memberId;
    private String title;
    private String body;
    private boolean blindStatus;
    private String blindDate;
    private boolean delStatus;
    private String delDate;
    private int hitCount;
    private int repliesCount;
    private int likeCount;
    private int dislikeCount;

    private Map<String, Object> extra;

    private String extra__writerName;

    public String getBodyForPrint() {
        String bodyForPrint = body.replaceAll("\r\n", "<br>");
        bodyForPrint = bodyForPrint.replaceAll("\r", "<br>");
        bodyForPrint = bodyForPrint.replaceAll("\n", "<br>");

        return bodyForPrint;
    }

    public String getWriterProfileImgUri() {
        return "/common/genFile/file/member/" + memberId + "/extra/profileImg/1";
    }

    public String getWriterProfileFallbackImgUri() {
        return "https://via.placeholder.com/300?text=^_^";
    }

    public String getWriterProfileFallbackImgOnErrorHtmlAttr() {
        return "this.src = '" + getWriterProfileFallbackImgUri() + "'";
    }
}