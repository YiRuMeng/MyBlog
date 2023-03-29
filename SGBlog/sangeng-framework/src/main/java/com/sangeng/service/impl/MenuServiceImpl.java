package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.constants.SystemConstants;
import com.sangeng.demo.ResponseResult;
import com.sangeng.demo.dto.AddMenuDto;
import com.sangeng.demo.entity.Menu;
import com.sangeng.demo.vo.MenuTreeVo;
import com.sangeng.demo.vo.MenuVo;
import com.sangeng.demo.vo.RoleMenuTreeSelectVo;
import com.sangeng.enums.AppHttpCodeEnum;
import com.sangeng.mapper.MenuMapper;
import com.sangeng.service.MenuService;
import com.sangeng.untils.BeanCopyUtils;
import com.sangeng.untils.SecurityUtils;
import com.sangeng.untils.SystemConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<String> selectPermsByUserId(Long id) {
        //如果是管理员，返回所有的权限
        if(id == 1L){
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType, SystemConstants.MENU,SystemConstants.BUTTON);
            wrapper.eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(wrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        //否则返回所具有的权限
        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<MenuVo> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<MenuVo> menus = null;
        //判断是否是管理员
        if(SecurityUtils.isAdmin()){
            //如果是 获取所有符合要求的Menu
            menus = menuMapper.selectAllRouterMenu();
        }else{
            //否则  获取当前用户所具有的Menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }

        //构建tree
        //先找出第一层的菜单  然后去找他们的子菜单设置到children属性中
        List<MenuVo> menuTree = builderMenuTree(menus,0L);
        return menuTree;
    }

    @Override
    public ResponseResult getMenuList(String status, String menuName) {
        // 根据menu状态和menuName查询
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like((StringUtils.hasText(menuName)),Menu::getMenuName,menuName)
                    .eq((StringUtils.hasText(status)),Menu::getStatus,status)
                    .orderByAsc(Menu::getParentId, Menu::getOrderNum);
        //获取当前用户所具有的Menu
        List<Menu> list = list(queryWrapper);

        //转换vo
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(list, MenuVo.class);

        return ResponseResult.okResult(menuVos);
    }

    @Override
    public ResponseResult addMenuList(AddMenuDto addMenuDto) {
        // 1.转换vo
        Menu menu = BeanCopyUtils.copyBean(addMenuDto, Menu.class);
        // 2.根据MenuName判断当前是否存在menu
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getMenuName, menu.getMenuName());
        Menu oneMenu = getOne(queryWrapper);
        if (!Objects.isNull(oneMenu)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.ADD_MENU_FAIL);
        }
        save(menu);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getMenuById(Long id) {
        Menu menu = getById(id);
        MenuVo menuVo = BeanCopyUtils.copyBean(menu, MenuVo.class);

        return ResponseResult.okResult(menuVo);
    }

    @Override
    public ResponseResult updateMenuList(AddMenuDto addMenuDto) {
        // 1.判断menuDto对象值是否为空
        if (!StringUtils.hasText(addMenuDto.getMenuName()) ||
                !StringUtils.hasText(addMenuDto.getMenuType()) ||
                !StringUtils.hasText(String.valueOf(addMenuDto.getStatus())) ||
                !StringUtils.hasText(addMenuDto.getPath()) ||
                !StringUtils.hasText(String.valueOf(addMenuDto.getOrderNum())) ||
                !StringUtils.hasText(addMenuDto.getIcon())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_MENU_NULL);
        }
        // 判断菜单的ParentId是否为本身
        if (addMenuDto.getId().equals(addMenuDto.getParentId())){
            return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_MENU_CF);
        }
        // 3.转换vo
        Menu menu = BeanCopyUtils.copyBean(addMenuDto, Menu.class);
        // 4.更新菜单信息
        updateById(menu);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteMenu(Long id) {
        // 1.查询是否存在子菜单
            //查询Menu表中的ParentId
        List<MenuVo> menuVos = getBaseMapper().selectAllMenu();
        for (MenuVo menuVo : menuVos) {
            if ( menuVo.getParentId().equals(id)){
               // 2.存在子菜单不允许删除 并且删除失败
               return ResponseResult.errorResult(AppHttpCodeEnum.EXIST_CHILDREN_TABLE);
           }
        }
        // 3.删除
        removeById(id);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getMenuTree() {
        List<MenuVo> menuVo = getBaseMapper().selectAllRouterMenu();
        List<MenuTreeVo> menuTreeVos = SystemConverter.buildMenuSelectTree(menuVo);

        return ResponseResult.okResult(menuTreeVos);
    }

    @Override
    public List<Long> selectMenuTreeByRoleId(Long roleId) {

        return getBaseMapper().selectMenuTreeByRoleId(roleId);
    }

    public List<Menu> selectMenuList(Menu menu) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        //排序 parent_id和order_num
        queryWrapper.orderByAsc(Menu::getParentId,Menu::getOrderNum);
        List<Menu> menus = list(queryWrapper);;
        return menus;
    }

    private List<MenuVo> builderMenuTree(List<MenuVo> menus, Long parentId) {
        List<MenuVo> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 获取存入参数的 子Menu集合
     * @param menu
     * @param menus
     * @return
     */
    private List<MenuVo> getChildren(MenuVo menu, List<MenuVo> menus) {
        List<MenuVo> childrenList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m->m.setChildren(getChildren(m,menus)))
                .collect(Collectors.toList());
        return childrenList;
    }
}