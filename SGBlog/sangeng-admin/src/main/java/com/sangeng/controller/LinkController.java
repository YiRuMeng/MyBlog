package com.sangeng.controller;

import com.sangeng.demo.ResponseResult;
import com.sangeng.demo.entity.Link;
import com.sangeng.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    /**
     * 分页友链列表
     */
    @GetMapping("/list")
    public ResponseResult list(Link link,Integer pageNum, Integer pageSize) {
        return linkService.selectLinkPage(link,pageNum,pageSize);
    }

    /**
     * 新增友链
     * @param link
     */
    @PostMapping()
    public ResponseResult add(@RequestBody Link link){
        linkService.save(link);

        return ResponseResult.okResult();
    }

    /**
     * 获取指定id的友链
     * @param id
     */
    @GetMapping("/{id}")
    public ResponseResult getLink(@PathVariable("id") Long id){

        Link link = linkService.getById(id);
        return ResponseResult.okResult(link);
    }

    /**
     * 更新友链
     * @param link
     */
    @PutMapping()
    public ResponseResult update(@RequestBody Link link){

        linkService.updateById(link);
        return ResponseResult.okResult();
    }

     /**
     * 删除友链
     * @param id
     */
    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable("id") Long id){

        linkService.removeById(id);
        return ResponseResult.okResult();
    }

}
