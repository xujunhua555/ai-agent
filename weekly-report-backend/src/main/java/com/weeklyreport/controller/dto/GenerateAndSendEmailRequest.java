package com.weeklyreport.controller.dto;

import lombok.Data;

import java.util.List;

/**
 * 一键生成周报并发送邮件的请求体，对应 Python MCP 工具 generate_and_send_email 的入参。
 */
@Data
public class GenerateAndSendEmailRequest {

    /**
     * JSON 数据文件路径（必填），对应 Python 工具的 json_data_file
     */
    private String jsonDataFile;

    /**
     * Key 描述 JSON 文件路径（必填），对应 Python 工具的 key_desc_file
     */
    private String keyDescFile;

    /**
     * 邮件主题，可选，对应 Python 工具的 subject，默认值由服务端决定
     */
    private String subject;

    /**
     * 邮件正文，可选，对应 Python 工具的 body，默认值由服务端决定
     */
    private String body;

    /**
     * 生成的周报 Excel 输出路径，可选，对应 Python 工具的 output_file
     */
    private String outputFile;

    /**
     * 收件人邮箱列表（必填），对应 Python 工具的 recipients；
     * 若为空将导致工具参数校验失败。
     */
    private List<String> recipients;


      /**发件人
     * sender
     */
      private String sender;


      /**发件人密码
     * sender password
     */
      private String password;
         /**发件人名称
     * sender password
     */
         private String name;
}
