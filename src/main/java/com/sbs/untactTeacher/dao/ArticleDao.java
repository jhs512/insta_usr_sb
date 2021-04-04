package com.sbs.untactTeacher.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbs.untactTeacher.dto.Article;
import com.sbs.untactTeacher.dto.Board;

@Mapper
public interface ArticleDao {
	boolean modifyArticle(@Param("id") int id, @Param("title") String title, @Param("body") String body);

	void writeArticle(@Param("boardId") int boardId, @Param("memberId") int memberId, @Param("title") String title, @Param("body") String body);

	Article getArticleById(@Param("id") int id);
	
	int getLastInsertId();

	void deleteArticleById(@Param("id") int id);

	Board getBoardById(@Param("id") int id);

	int getArticlesTotalCount(@Param("boardId") int boardId);
}
