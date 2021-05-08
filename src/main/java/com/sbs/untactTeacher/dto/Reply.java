package com.sbs.untactTeacher.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Reply {
    private int id;
    private String regDate;
    private String updateDate;
    private String relTypeCode;
    private int relId;
    private int memberId;
    private int parentId;
    private String body;
    private boolean blindStatus;
    private String blindDate;
    private boolean delStatus;
    private String delDate;
    private int likeCount;
    private int dislikeCount;

    private String extra__writerName;

    public String getBodyForPrint() {
        String bodyForPrint = body.replaceAll("\r\n", "<br>");
        bodyForPrint = bodyForPrint.replaceAll("\r", "<br>");
        bodyForPrint = bodyForPrint.replaceAll("\n", "<br>");

        return bodyForPrint;
    }
}