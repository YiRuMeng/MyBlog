package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.constants.SystemConstants;
import com.sangeng.demo.ResponseResult;
import com.sangeng.demo.entity.Link;
import com.sangeng.demo.vo.LinkVo;
import com.sangeng.demo.vo.PageVo;
import com.sangeng.mapper.LinkMapper;
import com.sangeng.service.LinkService;
import com.sangeng.untils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        //查询所有审核通过的
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(queryWrapper);
        //转换成vo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        //封装返回
        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult selectLinkPage(Link link, Integer pageNum, Integer pageSize) {
        //1.友链名称进行模糊查询
         //根据状态进行查询
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(link.getName()),Link::getName,link.getName());
        queryWrapper.eq(StringUtils.hasText(link.getStatus()),Link::getStatus,link.getStatus());

        //2.分页
        Page<Link> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        //3.转换Vo
        List<Link> linkList = page.getRecords();
        PageVo pageVo = new PageVo(linkList,page.getTotal());

        return ResponseResult.okResult(pageVo);
    }



}
