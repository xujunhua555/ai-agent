package com.weeklyreport.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmailRecipient {
    private Long id;
    /**
     * 用户名
     */
    private String name;
    /**
     * 邮箱地址
     */
    private String email;
    /**
     * 所属组别 ID
     */
    private Long groupId;
    /**
     * 组别名称（通过关联 email_group 查询得到）
     */
    private String groupName;
    private Integer isDefault;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
