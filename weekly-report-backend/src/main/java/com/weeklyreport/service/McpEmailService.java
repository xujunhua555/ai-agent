package com.weeklyreport.service;

import com.weeklyreport.controller.dto.GenerateAndSendEmailRequest;
import com.weeklyreport.controller.dto.SendEmailRequest;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.spec.McpSchema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class McpEmailService {

    private static final String EMAIL_CONNECTION_NAME = "email";

    @Value("${app.mcp.email-tool-name:send_email}")
    private String emailToolName;

    @Value("${app.mcp.generate-email-tool-name:generate_and_send_email}")
    private String generateEmailToolName;

    private final List<McpSyncClient> mcpSyncClients;

    private Optional<McpSyncClient> getEmailClient() {
        if (mcpSyncClients == null || mcpSyncClients.isEmpty()) {
            return Optional.empty();
        }
        Optional<McpSyncClient> byName = mcpSyncClients.stream()
                .filter(c -> EMAIL_CONNECTION_NAME.equals(getServerName(c)))
                .findFirst();
        if (byName.isPresent()) {
            return byName;
        }
        if (mcpSyncClients.size() == 1) {
            return Optional.of(mcpSyncClients.get(0));
        }
        return Optional.empty();
    }

    private String getServerName(McpSyncClient client) {
        try {
            return client.getServerInfo() != null ? client.getServerInfo().name() : null;
        } catch (Exception e) {
            return null;
        }
    }

    public List<String> listEmailToolNames() {
        return getEmailClient()
                .map(client -> {
                    try {
                        McpSchema.ListToolsResult result = client.listTools();
                        if (result != null && result.tools() != null) {
                            return result.tools().stream()
                                    .map(McpSchema.Tool::name)
                                    .toList();
                        }
                    } catch (Exception e) {
                        log.warn("列出 MCP 邮件工具失败", e);
                    }
                    return List.<String>of();
                })
                .orElse(List.of());
    }

    public String sendEmail(SendEmailRequest request) {
        return getEmailClient()
                .map(client -> {
                    String toolName = resolveSendEmailToolName(client);
                    Map<String, Object> args = new LinkedHashMap<>();
                    args.put("to", request.getTo());
                    args.put("subject", request.getSubject());
                    if (request.getBody() != null) {
                        args.put("body", request.getBody());
                    }
                    if (request.getCc() != null && !request.getCc().isBlank()) {
                        args.put("cc", request.getCc());
                    }
                    try {
                        McpSchema.CallToolResult result = client.callTool(
                            new McpSchema.CallToolRequest(toolName, args)
                        );
                        if (result != null && result.content() != null && !result.content().isEmpty()) {
                            return result.content().get(0).text();
                        }
                        return "MCP 工具调用成功，但返回内容为空";
                    } catch (Exception e) {
                        log.error("MCP 调用发送邮件失败", e);
                        return "调用失败：" + e.getMessage();
                    }
                })
                .orElse("未找到可用的 MCP 邮件客户端");
    }

    public String generateAndSendEmail(GenerateAndSendEmailRequest request) {
        return getEmailClient()
                .map(client -> {
                    Map<String, Object> args = new LinkedHashMap<>();
                    args.put("json_data_file", request.getJsonDataFile());
                    args.put("key_desc_file", request.getKeyDescFile());
                    if (request.getSubject() != null && !request.getSubject().isBlank()) {
                        args.put("subject", request.getSubject());
                    }
                    if (request.getBody() != null && !request.getBody().isBlank()) {
                        args.put("body", request.getBody());
                    }
                    if (request.getOutputFile() != null && !request.getOutputFile().isBlank()) {
                        args.put("output_file", request.getOutputFile());
                    }
                    args.put("recipients", request.getRecipients());
                    
                    try {
                        McpSchema.CallToolResult result = client.callTool(
                            new McpSchema.CallToolRequest(generateEmailToolName, args)
                        );
                        if (result != null && result.content() != null && !result.content().isEmpty()) {
                            return result.content().get(0).text();
                        }
                        return "MCP 工具调用成功，但返回内容为空";
                    } catch (Exception e) {
                        log.error("MCP 调用生成并发送邮件失败", e);
                        return "调用失败：" + e.getMessage();
                    }
                })
                .orElse("未找到可用的 MCP 邮件客户端");
    }

    private String resolveSendEmailToolName(McpSyncClient client) {
        try {
            McpSchema.ListToolsResult result = client.listTools();
            if (result == null || result.tools() == null || result.tools().isEmpty()) {
                return emailToolName;
            }
            List<String> names = result.tools().stream()
                    .map(McpSchema.Tool::name)
                    .toList();
            if (names.contains(emailToolName)) {
                return emailToolName;
            }
            Optional<String> sendLike = names.stream()
                    .filter(n -> n.toLowerCase().contains("send"))
                    .findFirst();
            if (sendLike.isPresent()) {
                return sendLike.get();
            }
            Optional<String> emailLike = names.stream()
                    .filter(n -> n.toLowerCase().contains("email"))
                    .findFirst();
            if (emailLike.isPresent()) {
                return emailLike.get();
            }
            if (names.size() == 1) {
                return names.get(0);
            }
            return emailToolName;
        } catch (Exception e) {
            log.warn("解析 MCP 邮件工具名失败，使用默认值", e);
            return emailToolName;
        }
    }
}
