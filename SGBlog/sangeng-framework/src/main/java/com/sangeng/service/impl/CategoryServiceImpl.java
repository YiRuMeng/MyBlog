package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.constants.SystemConstants;
import com.sangeng.demo.ResponseResult;
import com.sangeng.demo.entity.Article;
import com.sangeng.demo.entity.Category;
import com.sangeng.demo.entity.Role;
import com.sangeng.demo.vo.CategoryVo;
import com.sangeng.demo.vo.PageVo;
import com.sangeng.mapper.CategoryMapper;
import com.sangeng.service.ArticleService;
import com.sangeng.service.CategoryService;
import com.sangeng.untils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;



@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        //查询文章表  状态为已发布的文章
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);
        //获取文章的分类id，并且去重
        Set<Long> categoryIds = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());

        //查询分类表
        List<Category> categories = listByIds(categoryIds);
        categories = categories.stream().
                // 分类状态为0（启用）
                filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public List<CategoryVo> listAllCategory() {
        LambdaQueryWrapper<Category> categoryWrapper = new LambdaQueryWrapper<>();
        categoryWrapper.eq(Category::getStatus, SystemConstants.STATUS_NORMAL);
        List<Category> list= list(categoryWrapper);

        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(list, CategoryVo.class);
        return categoryVos;
    }

    @Override
    public ResponseResult selectCategory(Category category, Integer pageNum, Integer pageSize) {
        //1.根据名称和状态查询
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(category.getName()),Category::getName,category.getName());
        queryWrapper.eq(StringUtils.hasText(category.getStatus()),Category::getStatus,category.getStatus());

        //2.分页
        Page<Category> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        //3.转换vo对象
        List<Category> categoryList = page.getRecords();
        List<CategoryVo> categoryVoList = BeanCopyUtils.copyBeanList(categoryList, CategoryVo.class);
        PageVo pageVo = new PageVo(categoryVoList, page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult updateStatusById(Category category) {
        Category category1 = new Category();
        category1.setId(category.getId());
        category1.setStatus(category.getStatus());

        updateById(category1);
        return ResponseResult.okResult();
    }


}
