package com.sangeng.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.sangeng.constants.SystemConstants;
import com.sangeng.demo.ResponseResult;
import com.sangeng.demo.dto.UpdateRoleDto;
import com.sangeng.demo.entity.Category;
import com.sangeng.demo.vo.CategoryVo;
import com.sangeng.demo.vo.ExcelCategoryVo;
import com.sangeng.enums.AppHttpCodeEnum;
import com.sangeng.service.CategoryService;
import com.sangeng.untils.BeanCopyUtils;
import com.sangeng.untils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    
    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        List<CategoryVo> list = categoryService.listAllCategory();
        return ResponseResult.okResult(list);
    }

    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response){
        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            //获取需要导出的数据
            List<Category> categoryVos = categoryService.list();

            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categoryVos, ExcelCategoryVo.class);
            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);

        } catch (Exception e) {
            //如果出现异常也要响应json
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }

    }
    /**
     * 获取分类列表
     */
    @GetMapping("/list")
    public ResponseResult list(Category category,Integer pageNum, Integer pageSize){

        return categoryService.selectCategory(category,pageNum,pageSize);
    }

    @PostMapping
    public ResponseResult add(@RequestBody Category category){
        //逻辑删除赋值
        if(StringUtils.hasText(String.valueOf(category.getDelFlag()))){
            category.setDelFlag(SystemConstants.ARTICLE_STATUS_NORMAL);
        }
        categoryService.save(category);
        return ResponseResult.okResult(category);
    }

    /**
     *  修改分类：根据id获取分类详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult getInfo(@PathVariable("id") Long id){
        Category category = categoryService.getById(id);
        return ResponseResult.okResult(category);
    }

    /**
     *  修改分类：更新
     * @param category
     * @return
     */
    @PutMapping()
    public ResponseResult update(@RequestBody Category category){
        categoryService.updateById(category);
        return ResponseResult.okResult();
    }

    /**
     *  删除分类
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public ResponseResult remove(@PathVariable("id") Long id){
        categoryService.removeById(id);
        return ResponseResult.okResult();
    }

    /**
     *  改变分类状态
     * @param category
     * @return
     */
    @PutMapping("/changeStatus")
    public ResponseResult updateStatusById(@RequestBody Category category){

        return categoryService.updateStatusById(category);
    }



    
}