package com.weeklyreport.service;

import com.weeklyreport.entity.SysRole;
import com.weeklyreport.entity.SysUser;
import com.weeklyreport.mapper.SysPermissionMapper;
import com.weeklyreport.mapper.SysRoleMapper;
import com.weeklyreport.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final SysUserMapper sysUserMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysPermissionMapper sysPermissionMapper;

    public Map<String, Object> login(String username, String password) {
        SysUser user = sysUserMapper.selectByUsername(username);
        if (user == null) {
            log.warn("登录失败：用户不存在 {}", username);
            return null;
        }
        if (!password.equals(user.getPassword())) {
            log.warn("登录失败：密码错误 {}", username);
            return null;
        }
        if (user.getStatus() != null && user.getStatus() == 0) {
            log.warn("登录失败：用户已禁用 {}", username);
            return null;
        }
        user.setPassword(null);
        List<SysRole> roles = sysRoleMapper.selectByUserId(user.getId());
        List<String> permissions = sysPermissionMapper.selectCodesByUserId(user.getId());
        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        result.put("roles", roles);
        result.put("permissions", permissions);
        return result;
    }
}
