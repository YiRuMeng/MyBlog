package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.Exception.SystemException;
import com.sangeng.demo.ResponseResult;
import com.sangeng.demo.dto.TagListDto;
import com.sangeng.demo.entity.Tag;
import com.sangeng.demo.vo.PageVo;
import com.sangeng.demo.vo.TagVo;
import com.sangeng.enums.AppHttpCodeEnum;
import com.sangeng.mapper.TagMapper;
import com.sangeng.service.TagService;
import com.sangeng.untils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 标签(SgTag)表服务实现类
 *
 * @author makejava
 * @since 2023-03-13 12:04:19
 */
@Service("TagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public ResponseResult deleteTag(Integer id) {//删除标签
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        //分页查询
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
        queryWrapper.eq(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());

        Page<Tag> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);
        //封装数据返回
        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addTag(Tag tag) {//增加标签
        //标签名不能为空
        if(!StringUtils.hasText(tag.getName())){
            throw new SystemException(AppHttpCodeEnum.TAgNAME_NOT_NULL);
        }
        //标签备注不能为空
        if(!StringUtils.hasText(tag.getRemark())){
            throw new SystemException(AppHttpCodeEnum.TAgREMARK_NOT_NULL);
        }
        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public List<TagVo> listAllTag() {
        LambdaQueryWrapper<Tag> tagWrapper = new LambdaQueryWrapper<>();
        tagWrapper.select(Tag::getId,Tag::getName);
        List<Tag> list = list(tagWrapper);

        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(list,TagVo.class);
        return tagVos;
    }

}

