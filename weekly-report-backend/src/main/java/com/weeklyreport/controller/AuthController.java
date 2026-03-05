package com.weeklyreport.controller;

import com.weeklyreport.controller.vo.Result;
import com.weeklyreport.entity.SysUser;
import com.weeklyreport.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        if (username == null || password == null) {
            return Result.fail("用户名和密码不能为空");
        }
        Map<String, Object> loginResult = authService.login(username, password);
        if (loginResult == null) {
            return Result.fail(401, "用户名或密码错误");
        }
        SysUser user = (SysUser) loginResult.get("user");
        Map<String, Object> data = new HashMap<>();
        data.put("user", user);
        data.put("token", "token-" + user.getId());
        data.put("roles", loginResult.get("roles"));
        data.put("permissions", loginResult.get("permissions"));
        return Result.ok(data);
    }
}
