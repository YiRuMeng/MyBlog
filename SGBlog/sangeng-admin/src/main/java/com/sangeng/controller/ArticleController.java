package com.sangeng.controller;

import com.sangeng.demo.ResponseResult;
import com.sangeng.demo.dto.AddArticleDto;
import com.sangeng.demo.dto.SelectArticleDto;
import com.sangeng.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;



    @PostMapping//发布博客
    public ResponseResult add(@RequestBody AddArticleDto article){
        return articleService.add(article);
    }

    @GetMapping("/list")//文章管理查询
    public ResponseResult selectArticle(Integer pageNum, Integer pageSize, Long categoryId, String title, String summary){

        return articleService.articleListAll(pageNum,pageSize,categoryId,title,summary);
    }

    @GetMapping("/{id}")//查询文章详情接口
    public ResponseResult selectArticleId(@PathVariable("id") Long id){

        return articleService.getArticleDetailById(id);
    }

    @PutMapping()// 更新文章详情接口
    public ResponseResult updateArticle(@RequestBody SelectArticleDto article){

        return articleService.updateArticleInfo(article);
    }

    @DeleteMapping("/{id}")// 删除文章
    public ResponseResult deleteArticle(@PathVariable("id") Long id){

        return articleService.deleteArticleById(id);
    }


}