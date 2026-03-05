package com.weeklyreport.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SysUser {
    private Long id;
    private String username;
    private String password;
    private String realName;
    private String email;
    private Integer status; // 0 禁用 1 启用
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    /** 非表字段：用户拥有的角色列表 */
    private List<SysRole> roles;
}
