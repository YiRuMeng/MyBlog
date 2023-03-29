package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.demo.ResponseResult;
import com.sangeng.demo.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2023-03-06 13:13:57
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String articleComment, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);

}

