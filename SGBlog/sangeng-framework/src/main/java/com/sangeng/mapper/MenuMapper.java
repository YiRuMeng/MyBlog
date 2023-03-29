package com.sangeng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sangeng.demo.entity.Menu;
import com.sangeng.demo.vo.MenuVo;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-14 12:00:47
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<MenuVo> selectAllRouterMenu();

    List<MenuVo> selectRouterMenuTreeByUserId(Long userId);

    List<MenuVo> selectAllMenu();

    List<Long> selectMenuTreeByRoleId(Long id);


}

