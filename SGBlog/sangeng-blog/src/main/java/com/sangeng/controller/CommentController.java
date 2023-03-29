package com.sangeng.controller;

import com.sangeng.constants.SystemConstants;
import com.sangeng.demo.ResponseResult;
import com.sangeng.demo.dto.AddCommentDto;
import com.sangeng.demo.entity.Comment;
import com.sangeng.service.CommentService;
import com.sangeng.untils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@Api(tags = "评论",description ="评论相关接口" )
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 查询文章评论
     * @param articleId 文章id
     * @param pageNum 页数
     * @param pageSize 每页条数
     * @return
     */
    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize){
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }

    /**
     * 添加评论
     * @param addCommentDto
     * @return
     */
    @PostMapping("/addComment")
    public ResponseResult addComment(@RequestBody AddCommentDto addCommentDto){
        Comment comment = BeanCopyUtils.copyBean(addCommentDto, Comment.class);
        return commentService.addComment(comment);
    }

     /**
     * 查询友链评论
     * @param pageNum 页数
     * @param pageSize 每页条数
     * @return
     */
    @GetMapping("/linkCommentList")
    @ApiOperation(value = "友链评论列表",notes = "获取一页友链评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页号"),
            @ApiImplicitParam(name = "pageSize",value = "每页大小")
    }
    )
    public ResponseResult linkCommentList(Integer pageNum,Integer pageSize){
        return commentService.commentList(SystemConstants.LINK_COMMENT,null,pageNum,pageSize);
    }

}
