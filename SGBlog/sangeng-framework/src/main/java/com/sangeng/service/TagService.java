package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.demo.ResponseResult;
import com.sangeng.demo.dto.TagListDto;
import com.sangeng.demo.entity.Tag;
import com.sangeng.demo.vo.PageVo;
import com.sangeng.demo.vo.TagVo;

import java.util.List;


/**
 * 标签(SgTag)表服务接口
 *
 * @author makejava
 * @since 2023-03-13 12:04:19
 */
public interface TagService extends IService<Tag> {
    //删除
    ResponseResult deleteTag(Integer id);

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);
    //增加
    ResponseResult addTag(Tag tag);

    List<TagVo> listAllTag();
}

