package com.weeklyreport.controller;

import com.weeklyreport.controller.dto.UserSaveDTO;
import com.weeklyreport.controller.vo.Result;
import com.weeklyreport.entity.SysUser;
import com.weeklyreport.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/user")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService sysUserService;

    @GetMapping("/list")
    public Result<List<SysUser>> list(@RequestParam(required = false) String username) {
        return Result.ok(sysUserService.list(username));
    }

    @GetMapping("/{id}")
    public Result<SysUser> get(@PathVariable Long id) {
        return Result.ok(sysUserService.getById(id));
    }

    @PostMapping("/save")
    public Result<SysUser> save(@RequestBody UserSaveDTO dto) {
        SysUser user = new SysUser();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRealName(dto.getRealName());
        user.setEmail(dto.getEmail());
        user.setStatus(dto.getStatus() != null ? dto.getStatus() : 1);
        return Result.ok(sysUserService.save(user, dto.getRoleIds()));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        sysUserService.delete(id);
        return Result.ok(null);
    }
}
