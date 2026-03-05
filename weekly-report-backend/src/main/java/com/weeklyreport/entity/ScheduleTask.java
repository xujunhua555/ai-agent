package com.weeklyreport.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleTask {
    private Long id;
    private String taskName;
    private String cronExpression;
    /**
     * 发件人 ID
     */
    private Long senderId;
    /**
     * 收件人 ID 列表，逗号分隔
     */
    private String recipientIds;
    /**
     * 邮件主题
     */
    private String subject;
    /**
     * 邮件正文
     */
    private String content;
    private Integer enabled;
    private LocalDateTime lastRunTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
