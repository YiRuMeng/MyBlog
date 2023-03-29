package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.demo.ResponseResult;
import com.sangeng.demo.dto.AddArticleDto;
import com.sangeng.demo.dto.SelectArticleDto;
import com.sangeng.demo.entity.Article;



public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult add(AddArticleDto article);

    ResponseResult articleListAll(Integer pageNum, Integer pageSize,Long categoryId, String title, String summary);

    ResponseResult getArticleDetailById(Long id);


    ResponseResult updateArticleInfo(SelectArticleDto selectArticleDto);

    ResponseResult deleteArticleById(Long id);
}
