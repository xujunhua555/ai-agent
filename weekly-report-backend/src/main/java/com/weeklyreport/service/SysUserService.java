package com.weeklyreport.service;

import com.weeklyreport.entity.SysUser;
import com.weeklyreport.mapper.SysPermissionMapper;
import com.weeklyreport.mapper.SysRoleMapper;
import com.weeklyreport.mapper.SysUserMapper;
import com.weeklyreport.mapper.SysUserRoleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserService {

    private final SysUserMapper sysUserMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysPermissionMapper sysPermissionMapper;

    public List<SysUser> list(String username) {
        return sysUserMapper.selectList(username);
    }

    public SysUser getById(Long id) {
        return sysUserMapper.selectById(id);
    }

    @Transactional
    public SysUser save(SysUser user, List<Long> roleIds) {
        if (user.getId() == null) {
            sysUserMapper.insert(user);
        } else {
            sysUserMapper.update(user);
        }
        sysUserRoleMapper.deleteByUserId(user.getId());
        if (roleIds != null && !roleIds.isEmpty()) {
            for (Long roleId : roleIds) {
                sysUserRoleMapper.insert(user.getId(), roleId);
            }
        }
        return user;
    }

    @Transactional
    public void delete(Long id) {
        sysUserRoleMapper.deleteByUserId(id);
        sysUserMapper.deleteById(id);
    }
}
