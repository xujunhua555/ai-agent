package com.weeklyreport.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmailSender {
    private Long id;
    /**
     * 发件人名称（备注）
     */
    private String name;
    /**
     * 所属组别 ID
     */
    private Long groupId;
    /**
     * 组别名称（通过关联 email_group 查询得到）
     */
    private String groupName;
    /**
     * 发件邮箱地址
     */
    private String email;
    /**
     * 发件邮箱密码
     */
    private String password;
    /**
     * 是否默认发件人 0 否 1 是
     */
    private Integer isDefault;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
