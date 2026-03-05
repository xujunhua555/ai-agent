package com.weeklyreport.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SysRole {
    private Long id;
    private String code;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    /** 前端展示用：该角色下的权限 ID 列表 */
    private List<Long> permissionIds;
}
