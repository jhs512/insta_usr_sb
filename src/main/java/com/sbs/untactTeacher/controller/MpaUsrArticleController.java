package com.sbs.untactTeacher.controller;

import com.sbs.untactTeacher.dto.*;
import com.sbs.untactTeacher.service.ArticleService;
import com.sbs.untactTeacher.service.ReplyService;
import com.sbs.untactTeacher.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Slf4j
public class MpaUsrArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ReplyService replyService;

    @RequestMapping("/mpaUsr/article/detail")
    public String showDetail(HttpServletRequest req, int id) {
        Article article = articleService.getForPrintArticleById(id);
        List<Reply> replies = replyService.getForPrintRepliesByRelTypeCodeAndRelId("article", id);

        if (article == null) {
            return Util.msgAndBack(req, id + "번 게시물이 존재하지 않습니다.");
        }

        Board board = articleService.getBoardById(article.getBoardId());

        req.setAttribute("replies", replies);
        req.setAttribute("article", article);
        req.setAttribute("board", board);

        return "mpaUsr/article/detail";
    }

    @RequestMapping("/mpaUsr/article/write")
    public String showWrite(HttpServletRequest req, @RequestParam(defaultValue = "1") int boardId) {
        Board board = articleService.getBoardById(boardId);

        if (board == null) {
            return Util.msgAndBack(req, boardId + "번 게시판이 존재하지 않습니다.");
        }

        req.setAttribute("board", board);

        return "mpaUsr/article/write";
    }

    @RequestMapping("/mpaUsr/article/doWrite")
    public String doWrite(HttpServletRequest req, int boardId, String title, String body) {
        if (Util.isEmpty(title)) {
            return Util.msgAndBack(req, "제목을 입력해주세요.");
        }

        if (Util.isEmpty(body)) {
            return Util.msgAndBack(req, "내용을 입력해주세요.");
        }

        Rq rq = (Rq)req.getAttribute("rq");

        int memberId = rq.getLoginedMemberId();

        ResultData writeArticleRd = articleService.writeArticle(boardId, memberId, title, body);

        if (writeArticleRd.isFail()) {
            return Util.msgAndBack(req, writeArticleRd.getMsg());
        }

        String replaceUri = "detail?id=" + writeArticleRd.getBody().get("id");
        return Util.msgAndReplace(req, writeArticleRd.getMsg(), replaceUri);
    }

    @RequestMapping("/mpaUsr/article/doModify")
    @ResponseBody
    public ResultData doModify(Integer id, String title, String body) {

        if (Util.isEmpty(id)) {
            return new ResultData("F-1", "번호를 입력해주세요.");
        }

        if (Util.isEmpty(title)) {
            return new ResultData("F-2", "제목을 입력해주세요.");
        }

        if (Util.isEmpty(body)) {
            return new ResultData("F-3", "내용을 입력해주세요.");
        }

        Article article = articleService.getArticleById(id);

        if (article == null) {
            return new ResultData("F-4", "존재하지 않는 게시물 번호입니다.");
        }

        return articleService.modifyArticle(id, title, body);
    }

    @RequestMapping("/mpaUsr/article/doDelete")

    public String doDelete(HttpServletRequest req, Integer id) {
        if (Util.isEmpty(id)) {
            return Util.msgAndBack(req, "id를 입력해주세요.");
        }

        ResultData rd = articleService.deleteArticleById(id);

        if (rd.isFail()) {
            return Util.msgAndBack(req, rd.getMsg());
        }

        String redirectUri = "../article/list?boardId=" + rd.getBody().get("boardId");

        return Util.msgAndReplace(req, rd.getMsg(), redirectUri);
    }

    @RequestMapping("/mpaUsr/article/list")
    public String showList(HttpServletRequest req, @RequestParam(defaultValue = "1") int boardId, String searchKeywordType, String searchKeyword,
                           @RequestParam(defaultValue = "1") int page) {
        Board board = articleService.getBoardById(boardId);

        if (Util.isEmpty(searchKeywordType)) {
            searchKeywordType = "titleAndBody";
        }

        if (board == null) {
            return Util.msgAndBack(req, boardId + "번 게시판이 존재하지 않습니다.");
        }

        req.setAttribute("board", board);

        int totalItemsCount = articleService.getArticlesTotalCount(boardId, searchKeywordType, searchKeyword);

        if (searchKeyword == null || searchKeyword.trim().length() == 0) {

        }

        req.setAttribute("totalItemsCount", totalItemsCount);

        // 한 페이지에 보여줄 수 있는 게시물 최대 개수
        int itemsCountInAPage = 20;
        // 총 페이지 수
        int totalPage = (int) Math.ceil(totalItemsCount / (double) itemsCountInAPage);

        // 현재 페이지(임시)
        req.setAttribute("page", page);
        req.setAttribute("totalPage", totalPage);

        List<Article> articles = articleService.getForPrintArticles(boardId, searchKeywordType, searchKeyword, itemsCountInAPage, page);

        req.setAttribute("articles", articles);

        return "mpaUsr/article/list";
    }

    @RequestMapping("/mpaUsr/article/getArticle")
    @ResponseBody
    public ResultData getArticle(Integer id) {
        if (Util.isEmpty(id)) {
            return new ResultData("F-1", "번호를 입력해주세요.");
        }

        Article article = articleService.getArticleById(id);

        if (article == null) {
            return new ResultData("F-1", id + "번 글은 존재하지 않습니다.", "id", id);
        }

        return new ResultData("S-1", article.getId() + "번 글 입니다.", "article", article);
    }
}
