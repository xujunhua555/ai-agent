package com.weeklyreport.controller;

import com.weeklyreport.controller.vo.Result;
import com.weeklyreport.entity.SysPermission;
import com.weeklyreport.service.SysPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/permission")
@RequiredArgsConstructor
public class SysPermissionController {

    private final SysPermissionService sysPermissionService;

    @GetMapping("/tree")
    public Result<List<SysPermission>> tree() {
        return Result.ok(sysPermissionService.listTree());
    }

    @GetMapping("/list")
    public Result<List<SysPermission>> list() {
        return Result.ok(sysPermissionService.listAll());
    }
}
