package com.weeklyreport.controller;

import com.weeklyreport.controller.dto.GenerateAndSendEmailRequest;
import com.weeklyreport.controller.dto.SendEmailRequest;
import com.weeklyreport.controller.vo.Result;
import com.weeklyreport.service.McpEmailService;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController { 

    private final ChatClient chatClient;
    private final McpEmailService mcpEmailService;

    public TestController(ChatClient.Builder chatClientBuilder, McpEmailService mcpEmailService) {
        this.chatClient = chatClientBuilder.build();
        this.mcpEmailService = mcpEmailService;
    }

    @GetMapping("/ai")
    public Result<Map<String, String>> ai(
            @RequestParam(defaultValue = "用一句话介绍你自己") String message) {
        String content = chatClient.prompt()
                .user(message)
                .call()
                .content();
        return Result.ok(Map.of(
                "prompt", message,
                "reply", content
        ));
    }

    @PostMapping("/email/send")
    public Result<Map<String, String>> sendEmail(@RequestBody SendEmailRequest request) {
        if (request.getTo() == null || request.getTo().isBlank()) {
            return Result.fail("收件人不能为空");
        }
        if (request.getSubject() == null || request.getSubject().isBlank()) {
            return Result.fail("邮件主题不能为空");
        }
        String result = mcpEmailService.sendEmail(request);
        return Result.ok(Map.of(
                "message", "发送请求已提交",
                "detail", result
        ));
    }

    @PostMapping("/email/generate-and-send")
    public Result<Map<String, String>> generateAndSendEmail(@RequestBody GenerateAndSendEmailRequest request) {
        if (request.getJsonDataFile() == null || request.getJsonDataFile().isBlank()) {
            return Result.fail("jsonDataFile（JSON 数据文件路径）不能为空");
        }
        if (request.getKeyDescFile() == null || request.getKeyDescFile().isBlank()) {
            return Result.fail("keyDescFile（Key 描述文件路径）不能为空");
        }
        if (request.getRecipients() == null || request.getRecipients().isEmpty()) {
            return Result.fail("recipients（收件人列表）不能为空");
        }
        String result = mcpEmailService.generateAndSendEmail(request);
        return Result.ok(Map.of(
                "message", "生成周报并发送邮件的请求已提交",
                "detail", result
        ));
    }

    @GetMapping("/email/tools")
    public Result<List<String>> listEmailTools() {
        List<String> tools = mcpEmailService.listEmailToolNames();
        return Result.ok(tools);
    }
}
