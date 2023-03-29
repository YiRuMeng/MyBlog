package com.sangeng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sangeng.demo.entity.ArticleTag;

import java.util.List;


/**
 * 文章标签关联表(ArticleTag)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-15 17:57:00
 */
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

    List<Long> selectTagById(Long id);
}

