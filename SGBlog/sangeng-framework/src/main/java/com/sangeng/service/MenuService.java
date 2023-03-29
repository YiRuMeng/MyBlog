package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.demo.ResponseResult;
import com.sangeng.demo.dto.AddMenuDto;
import com.sangeng.demo.entity.Menu;
import com.sangeng.demo.vo.MenuTreeVo;
import com.sangeng.demo.vo.MenuVo;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2023-03-14 12:00:45
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<MenuVo> selectRouterMenuTreeByUserId(Long userId);

    ResponseResult getMenuList(String status, String menuName);

    ResponseResult addMenuList(AddMenuDto addMenuDto);

    ResponseResult getMenuById(Long id);

    ResponseResult updateMenuList(AddMenuDto addMenuDto);

    ResponseResult deleteMenu(Long id);

    ResponseResult getMenuTree();

    List<Long> selectMenuTreeByRoleId(Long roleId);

    List<Menu> selectMenuList(Menu menu);
}

