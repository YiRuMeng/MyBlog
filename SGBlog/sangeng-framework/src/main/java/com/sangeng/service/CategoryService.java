package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.demo.ResponseResult;
import com.sangeng.demo.entity.Category;
import com.sangeng.demo.vo.CategoryVo;

import java.io.Serializable;
import java.util.List;


public interface CategoryService extends IService<Category> {
    ResponseResult getCategoryList();


    List<CategoryVo> listAllCategory();


    ResponseResult selectCategory(Category category, Integer pageNum, Integer pageSize);

    ResponseResult updateStatusById(Category category);
}
