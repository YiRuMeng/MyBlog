package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.demo.entity.RoleMenu;


/**
 * 角色和菜单关联表(RoleMenu)表服务接口
 *
 * @author makejava
 * @since 2023-03-27 14:11:17
 */
public interface RoleMenuService extends IService<RoleMenu> {

    void deleteRoleMenuByRoleId(Long id);
}

