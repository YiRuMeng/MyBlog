package com.sangeng.controller;

import com.sangeng.demo.ResponseResult;
import com.sangeng.demo.dto.AddMenuDto;
import com.sangeng.demo.entity.Menu;
import com.sangeng.demo.vo.MenuTreeVo;
import com.sangeng.demo.vo.MenuVo;
import com.sangeng.demo.vo.RoleMenuTreeSelectVo;
import com.sangeng.service.MenuService;
import com.sangeng.untils.BeanCopyUtils;
import com.sangeng.untils.SystemConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @title: MenuController
 * @Author: hjj
 * @Date:
 * @Version 1.0
 */
@RestController
@RequestMapping("system/menu")
public class MenuController {


    @Autowired
    private MenuService menuService;


    /**
     *  查询菜单
     * @param: status 状态
     * @param: menuName 菜单名
     * @return
     */

    @GetMapping("/list")
    public ResponseResult getMenuList(String status, String menuName){

        return menuService.getMenuList(status,menuName);
    }

    /**
     *  添加菜单
     * @param addMenuDto
     * @return
     */
    @PostMapping()
    public ResponseResult addMenuList(@RequestBody AddMenuDto addMenuDto){

        return menuService.addMenuList(addMenuDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getMenuById(@PathVariable("id") Long id){
        return menuService.getMenuById(id);

    }

    /**
     *  更新菜单
     * @param addMenuDto
     * @return
     */
    @PutMapping("")
    public ResponseResult updateMenuList(@RequestBody AddMenuDto addMenuDto){

        return menuService.updateMenuList(addMenuDto);
    }

    @DeleteMapping("/{menuId}")
    public ResponseResult deleteMenu(@PathVariable("menuId") Long id){

        return menuService.deleteMenu(id);
    }

    /**
     *  获取菜单树
     * @return
     */
    @GetMapping("/treeselect")
    public ResponseResult getMenuTree(){
        return menuService.getMenuTree();
    }

    /**
     * 加载对应角色菜单列表树
     */
    @GetMapping("/roleMenuTreeselect/{roleId}")
    public ResponseResult getMenuTreeById(@PathVariable("roleId") Long roleId){
        // 数据库查询菜单列表
        List<Menu> menus = menuService.selectMenuList(new Menu());
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menus,MenuVo.class);
        // 查询对应角色菜单列表数id
        List<Long> checkedKeys = menuService.selectMenuTreeByRoleId(roleId);
        List<MenuTreeVo> menuTreeVos = SystemConverter.buildMenuSelectTree(menuVos);
        //转换vo
        RoleMenuTreeSelectVo vo = new RoleMenuTreeSelectVo(checkedKeys,menuTreeVos);
        return ResponseResult.okResult(vo);
    }

}
