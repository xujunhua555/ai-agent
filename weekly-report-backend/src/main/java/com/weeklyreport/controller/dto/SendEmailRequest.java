package com.weeklyreport.controller.dto;

import lombok.Data;

/**
 * 测试发送邮件请求体，用于调用 MCP 邮件工具的入参。
 */
@Data
public class SendEmailRequest {

    /** 收件人邮箱，必填 */
    private String to;

    /** 邮件主题，必填 */
    private String subject;

    /** 邮件正文（纯文本） */
    private String body;

    /** 抄送，可选，多个用逗号分隔 */
    private String cc;
}
