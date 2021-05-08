package com.sbs.untactTeacher.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReplyDao {
    void write(@Param("relTypeCode") String relTypeCode,
               @Param("relId") int relId,
               @Param("memberId") int memberId,
               @Param("body") String body);

    int getLastInsertId();
}
