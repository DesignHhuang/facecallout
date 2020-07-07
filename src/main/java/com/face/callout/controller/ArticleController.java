package com.face.callout.controller;

import com.face.callout.common.restmodel.RestResult;
import com.face.callout.common.restmodel.RestResultBuilder;
import com.face.callout.entity.Article;
import com.face.callout.entity.Comment;
import com.face.callout.entity.Reply;
import com.face.callout.entity.Zan;
import com.face.callout.repository.ArticleRepository;
import com.face.callout.repository.CommentRepository;
import com.face.callout.repository.ReplyRepository;
import com.face.callout.repository.ZanRepository;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/article")
public class ArticleController extends BaseController {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    ZanRepository zanRepository;

    //获取所有的文章
    @PostMapping(value = "/allArticles")
    @SuppressWarnings("unchecked")
    public RestResult allArticles(@RequestBody @Valid JSONObject data) {
        int page = data.getInt("pageIndex");
        int size = data.getInt("pageSize");
        Pageable pageable = new PageRequest(page, size);
        Page<Article> articleList = articleRepository.findAllByIsdeletedFalseOrderByCreatedAtDesc(pageable);
        return RestResultBuilder.builder().success(articleList).build();
    }

    //获取本人的文章
    @PostMapping(value = "/allArticlesByUser")
    @SuppressWarnings("unchecked")
    public RestResult allArticlesByUser(@RequestBody @Valid JSONObject data) {
        int page = data.getInt("pageIndex");
        int size = data.getInt("pageSize");
        Pageable pageable = new PageRequest(page, size);
        Page<Article> articleList = articleRepository.findAllByCreatorAndIsdeletedFalseOrderByCreatedAtDesc(getCurrentUser(), pageable);
        //获取每篇文章的点赞数量
        for (Article article : articleList) {

        }
        return RestResultBuilder.builder().success(articleList).build();
    }

    //创建
    @PostMapping(value = "/create")
    @SuppressWarnings("unchecked")
    public RestResult create(@RequestBody @Valid Article article) {
        article.setCreator(getCurrentUser());
        articleRepository.save(article);
        return RestResultBuilder.builder().success(article).build();
    }

    //点赞，取消点赞
    @PostMapping(value = "/dolike")
    @SuppressWarnings("unchecked")
    public RestResult dolike(@RequestBody @Valid JSONObject data) {
        long type_id = data.getLong("id");
        String type_name = data.getString("type");
        Object result = null;
        Article article = null;
        Comment comment = null;
        Reply reply = null;
        switch (type_name) {
            case "文章点赞":
                article = articleRepository.getOne(type_id);
            case "评论点赞":
                comment = commentRepository.getOne(type_id);
            case "回复点赞":
                reply = replyRepository.getOne(type_id);
        }
        Zan zanexist = zanRepository.findZanByTypenameAndTypeidAndCreator(type_name, type_id, getCurrentUser());
        if (zanexist != null) {
            zanexist.setIsdeleted(!zanexist.isIsdeleted());
            zanRepository.save(zanexist);
            switch (type_name) {
                case "文章点赞":
                    if (zanexist.isIsdeleted()) {
                        article.setLikesum(article.getLikesum() - 1);
                    } else {
                        article.setLikesum(article.getLikesum() + 1);
                    }
                    result = articleRepository.save(article);
                    break;
                case "评论点赞":
                    if (zanexist.isIsdeleted()) {
                        comment.setLikesum(comment.getLikesum() - 1);
                    } else {
                        comment.setLikesum(comment.getLikesum() + 1);
                    }
                    result = commentRepository.save(comment);
                    break;
                case "回复点赞":
                    if (zanexist.isIsdeleted()) {
                        reply.setLikesum(reply.getLikesum() - 1);
                    } else {
                        reply.setLikesum(reply.getLikesum() + 1);
                    }
                    result = replyRepository.save(reply);
                    break;
            }
        } else {
            Zan zan = new Zan();
            zan.setTypename(type_name);
            zan.setTypeid(type_id);
            zan.setCreator(getCurrentUser());
            zanRepository.save(zan);
            switch (type_name) {
                case "文章点赞":
                    article.setLikesum(article.getLikesum() + 1);
                    result = articleRepository.save(article);
                    break;
                case "评论点赞":
                    comment.setLikesum(comment.getLikesum() + 1);
                    result = commentRepository.save(comment);
                    break;
                case "回复点赞":
                    reply.setLikesum(reply.getLikesum() + 1);
                    result = replyRepository.save(reply);
                    break;
            }
        }
        return RestResultBuilder.builder().success(result).build();
    }

    //评论
    /*@PostMapping(value = "/createcomment")
    @SuppressWarnings("unchecked")
    public RestResult createcomment(@RequestBody @Valid JSONObject data) {
        article.setCreator(getCurrentUser());
        articleRepository.save(article);
        return RestResultBuilder.builder().success(article).build();
    }*/

    //  删除
    @PostMapping(value = "/delete")
    @SuppressWarnings("unchecked")
    public RestResult delete(@RequestBody @Valid Article article) {
        article.setIsdeleted(true);
        articleRepository.save(article);
        return RestResultBuilder.builder().success(article).build();
    }
}
