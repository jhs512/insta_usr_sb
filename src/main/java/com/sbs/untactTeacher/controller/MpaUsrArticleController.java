package com.sbs.untactTeacher.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.untactTeacher.dto.Article;
import com.sbs.untactTeacher.dto.ResultData;
import com.sbs.untactTeacher.util.Util;

@Controller
public class MpaUsrArticleController {
	private int articleLastId = 0;

	@RequestMapping("/mpaUsr/article/doWrite")
	@ResponseBody
	public ResultData doWrite(String title, String body) {
		int id = articleLastId + 1;
		String regDate = Util.getNowDateStr();
		String updateDate = Util.getNowDateStr();
		
		Article article = new Article(id, regDate, updateDate, title, body);

		articleLastId = id;

		return new ResultData("S-1", id + "번 글이 작성되었습니다.", "article", article);
	}
}




