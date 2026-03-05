package com.weeklyreport.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SysPermission {
    private Long id;
    private Long parentId;
    private String code;
    private String name;
    private String type; // menu / button
    private Integer sortOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    /** 树形子节点 */
    private List<SysPermission> children;
}
