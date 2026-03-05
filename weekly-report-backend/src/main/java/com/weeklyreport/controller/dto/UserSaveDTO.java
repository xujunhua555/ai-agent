package com.weeklyreport.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserSaveDTO {
    private Long id;
    private String username;
    private String password;
    private String realName;
    private String email;
    private Integer status;
    private List<Long> roleIds;
}
