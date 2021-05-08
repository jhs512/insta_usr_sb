package com.sbs.untactTeacher.dao;

import com.sbs.untactTeacher.dto.Reply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReplyDao {
    void write(@Param("relTypeCode") String relTypeCode,
               @Param("relId") int relId,
               @Param("memberId") int memberId,
               @Param("body") String body);

    int getLastInsertId();

    List<Reply> getForPrintRepliesByRelTypeCodeAndRelId(
            @Param("relTypeCode") String relTypeCode,
            @Param("relId") int relId);

    Reply getReplyById(@Param("id") int id);

    void delete(@Param("id") int id);
}
