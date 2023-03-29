package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.constants.SystemConstants;
import com.sangeng.demo.ResponseResult;
import com.sangeng.demo.dto.RoleDto;
import com.sangeng.demo.dto.UpdateRoleDto;
import com.sangeng.demo.entity.Role;
import com.sangeng.demo.entity.RoleMenu;
import com.sangeng.demo.vo.AdminRoleVo;
import com.sangeng.demo.vo.RoleVo;
import com.sangeng.enums.AppHttpCodeEnum;
import com.sangeng.mapper.RoleMapper;
import com.sangeng.service.RoleMenuService;
import com.sangeng.service.RoleService;
import com.sangeng.untils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-03-14 12:06:25
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {


    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否是管理员 如果是返回集合中只需要有admin
        if(id == 1L){
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //否则查询用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult getRoleList(Integer pageNum, Integer pageSize, RoleDto roleDto) {
        // 1.根据友链名(模糊查询)和状态进行查询
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(roleDto.getStatus()), Role::getStatus, roleDto.getStatus());
        queryWrapper.like(StringUtils.hasText(roleDto.getRoleName()), Role::getRoleName, roleDto.getRoleName());
        // 2.要求按照role_sort进行升序排列。
        queryWrapper.orderByDesc(Role::getRoleSort);

        //分页查询
        Page<Role> page = new Page(pageNum,pageSize);
        page(page,queryWrapper);
        // 3.将当前页中的Role对象转换为RoleVo对象
        List<Role> roles = page.getRecords();
        List<RoleVo> roleVos = BeanCopyUtils.copyBeanList(roles, RoleVo.class);
        // 4.将RoleVo对象转换为AdminRoleVo对象
        AdminRoleVo adminRoleVo = new AdminRoleVo(roleVos, page.getTotal());
        return ResponseResult.okResult(adminRoleVo);
    }

    @Override
    public ResponseResult updateStatusById(UpdateRoleDto updateRoleDto) {
        Role role = new Role();
        role.setId(updateRoleDto.getRoleid());
        role.setStatus(updateRoleDto.getStatus());

        updateById(role);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult addRole(Role role) {

        save(role);

        return ResponseResult.okResult(role);
    }

    @Override
    public ResponseResult updateRole(Role role) {
        //判断更新内容是否为空
        if (!StringUtils.hasText(role.getRoleName())
                ||!StringUtils.hasText(role.getRoleKey())
                ||!StringUtils.hasText(String.valueOf(role.getRoleSort()))){
            return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_MENU_NULL);
        }
        updateById(role);
        // 删除角色菜单关联表的数据重新插入
        roleMenuService.deleteRoleMenuByRoleId(role.getId());
        insertRoleMenu(role);
        return ResponseResult.okResult();
    }

    @Override
    public List<Role> selectAllRole() {
        return list(Wrappers.<Role>lambdaQuery().eq(Role::getStatus, SystemConstants.NORMAL));
    }

    @Override
    public List<Long> selectRoleIdByUserId(Long userId) {
        return getBaseMapper().selectRoleIdByUserId(userId);
    }

    /**
     *  角色与菜单关联表插入数据
     * @param role
     */
    private void insertRoleMenu(Role role) {
        List<RoleMenu> roleMenuList = Arrays.stream(role.getMenuIds())
                .map(memuId -> new RoleMenu(role.getId(), memuId))
                .collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenuList);
    }
}

