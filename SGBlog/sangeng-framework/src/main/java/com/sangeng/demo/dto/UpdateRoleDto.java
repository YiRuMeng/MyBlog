package com.sangeng.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoleDto {

    //角色ID
    private Long roleid;
    //角色状态（0正常 1停用）
    private String status;

}
