package com.weeklyreport.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmailGroup {
    private Long id;
    /**
     * 组别名称，例如"服务端开发组"
     */
    private String name;
    private Integer sortOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
