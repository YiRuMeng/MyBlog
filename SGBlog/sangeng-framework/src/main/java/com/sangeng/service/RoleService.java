package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.demo.ResponseResult;
import com.sangeng.demo.dto.RoleDto;
import com.sangeng.demo.dto.UpdateRoleDto;
import com.sangeng.demo.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-03-14 12:06:25
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult getRoleList(Integer pageNum, Integer pageSize, RoleDto roleDto);

    ResponseResult updateStatusById(UpdateRoleDto updateRoleDto);

    ResponseResult addRole(Role addRoleDto);

    ResponseResult updateRole(Role role);

    List<Role> selectAllRole();

    List<Long> selectRoleIdByUserId(Long userId);
}

