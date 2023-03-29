package com.sangeng.controller;


import com.sangeng.demo.ResponseResult;
import com.sangeng.demo.dto.RoleDto;
import com.sangeng.demo.dto.UpdateRoleDto;
import com.sangeng.demo.dto.UpdateRoleIdDto;
import com.sangeng.demo.entity.Role;
import com.sangeng.service.RoleService;
import com.sangeng.untils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;


    /**
     *  分页查询角色列表
     * @param pageNum
     * @param pageSize
     * @param roleDto
     * @return
     */
    @GetMapping("/list")
    public ResponseResult getRoleList(Integer pageNum, Integer pageSize, RoleDto roleDto){

        return roleService.getRoleList(pageNum,pageSize,roleDto);
    }

    /**
     *  改变角色状态
     * @param updateRoleDto
     * @return
     */
    @PutMapping("/changeStatus")
    public ResponseResult updateStatusById(@RequestBody UpdateRoleDto updateRoleDto){

        return roleService.updateStatusById(updateRoleDto);
    }

    /**
     *  添加角色
     * @param role
     * @return
     */
    @PostMapping
    public ResponseResult addRole(@RequestBody Role role){
        return roleService.addRole(role);
    }

    /**
     * 修改角色：根据角色编号获取详细信息
     */
    @GetMapping("{roleId}")
    public ResponseResult getById(@PathVariable("roleId") Long id){
        //1.根据id获取角色信息
        Role role = roleService.getById(id);
        //2.转换vo
        UpdateRoleIdDto updateRoleIdDto = BeanCopyUtils.copyBean(role, UpdateRoleIdDto.class);

        return ResponseResult.okResult(updateRoleIdDto);
    }

    /**
     * 修改角色：更新角色
     */
    @PutMapping
    public ResponseResult updateById(@RequestBody Role role){

        return roleService.updateRole(role);
    }

    @DeleteMapping("/{id}")
    public ResponseResult remove(@PathVariable("id") Long id){
        roleService.removeById(id);
        return ResponseResult.okResult();
    }

    @GetMapping("/listAllRole")
    public ResponseResult listAllRole(){

        return ResponseResult.okResult(roleService.selectAllRole());
    }

}
