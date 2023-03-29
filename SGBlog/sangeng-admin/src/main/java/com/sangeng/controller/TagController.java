package com.sangeng.controller;

import com.sangeng.demo.ResponseResult;
import com.sangeng.demo.dto.TagListDto;
import com.sangeng.demo.entity.Tag;
import com.sangeng.demo.vo.PageVo;
import com.sangeng.demo.vo.TagVo;
import com.sangeng.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }

    @PostMapping()//新增标签
    public ResponseResult addTag(@RequestBody Tag tag ){
        return tagService.addTag(tag);
    }

    @DeleteMapping("{id}")//删除标签
    public ResponseResult deleteTag(@PathVariable("id") Integer id){
        return tagService.deleteTag(id);
    }

    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        List<TagVo> list = tagService.listAllTag();
        return ResponseResult.okResult(list);
    }
}