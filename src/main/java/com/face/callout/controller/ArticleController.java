package com.face.callout.controller;

import com.face.callout.common.restmodel.RestResult;
import com.face.callout.common.restmodel.RestResultBuilder;
import com.face.callout.entity.Article;
import com.face.callout.repository.ArticleRepository;
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
public class ArticleController extends BaseController{

    @Autowired
    ArticleRepository articleRepository;

    //获取所有的文章
    @PostMapping(value = "/allArticles")
    @SuppressWarnings("unchecked")
    public RestResult allArticles(@RequestBody @Valid JSONObject data) {
        int page = data.getInt("pageIndex");
        int size = data.getInt("pageSize");
        Pageable pageable = new PageRequest(page, size);
        Page<Article> articleList =  articleRepository.findAllByIsdeletedFalseOrderByCreatedAtDesc(pageable);
        return RestResultBuilder.builder().success(articleList).build();
    }

    //获取本人的文章
    @PostMapping(value = "/allArticlesByUser")
    @SuppressWarnings("unchecked")
    public RestResult allArticlesByUser(@RequestBody @Valid JSONObject data) {
        int page = data.getInt("pageIndex");
        int size = data.getInt("pageSize");
        Pageable pageable = new PageRequest(page, size);
        Page<Article> articleList =  articleRepository.findAllByCreatorAndIsdeletedFalseOrderByCreatedAtDesc(getCurrentUser(),pageable);
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

    //  删除
    @PostMapping(value = "/delete")
    @SuppressWarnings("unchecked")
    public RestResult delete(@RequestBody @Valid Article article) {
        article.setIsdeleted(true);
        articleRepository.save(article);
        return RestResultBuilder.builder().success(article).build();
    }
}
