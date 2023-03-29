package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.sangeng.demo.entity.ArticleTag;

import java.util.List;


/**
 * 文章标签关联表(ArticleTag)表服务接口
 *
 * @author makejava
 * @since 2023-03-15 17:56:59
 */
public interface ArticleTagService extends IService<ArticleTag> {
    List<Long> selectTagById (Long id);


}

