package com.sangeng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.sangeng.demo.entity.ArticleTag;
import com.sangeng.demo.entity.Tag;
import com.sangeng.mapper.ArticleTagMapper;
import com.sangeng.service.ArticleTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author makejava
 * @since 2023-03-15 17:57:00
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {


    @Override
    public List<Long> selectTagById(Long id) {
        return getBaseMapper().selectTagById(id);
    }
}

