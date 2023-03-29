package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.demo.ResponseResult;
import com.sangeng.demo.entity.Link;


public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult selectLinkPage(Link link, Integer pageNum, Integer pageSize);



}

