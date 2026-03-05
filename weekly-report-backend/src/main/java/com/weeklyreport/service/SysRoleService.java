package com.weeklyreport.service;

import com.weeklyreport.entity.SysRole;
import com.weeklyreport.mapper.SysPermissionMapper;
import com.weeklyreport.mapper.SysRoleMapper;
import com.weeklyreport.mapper.SysRolePermissionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SysRoleService {

    private final SysRoleMapper sysRoleMapper;
    private final SysRolePermissionMapper sysRolePermissionMapper;
    private final SysPermissionMapper sysPermissionMapper;

    public List<SysRole> listAll() {
        return sysRoleMapper.selectAll();
    }

    public SysRole getById(Long id) {
        SysRole role = sysRoleMapper.selectById(id);
        if (role != null) {
            List<Long> permissionIds = sysPermissionMapper.selectPermissionIdsByRoleId(id);
            role.setPermissionIds(permissionIds);
        }
        return role;
    }

    @Transactional
    public SysRole save(SysRole role) {
        if (role.getId() == null) {
            sysRoleMapper.insert(role);
        } else {
            sysRoleMapper.update(role);
        }
        sysRolePermissionMapper.deleteByRoleId(role.getId());
        if (role.getPermissionIds() != null && !role.getPermissionIds().isEmpty()) {
            for (Long pid : role.getPermissionIds()) {
                sysRolePermissionMapper.insert(role.getId(), pid);
            }
        }
        return role;
    }

    @Transactional
    public void delete(Long id) {
        sysRolePermissionMapper.deleteByRoleId(id);
        sysRoleMapper.deleteById(id);
    }
}
