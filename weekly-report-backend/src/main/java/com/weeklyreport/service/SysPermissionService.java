package com.weeklyreport.service;

import com.weeklyreport.entity.SysPermission;
import com.weeklyreport.mapper.SysPermissionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SysPermissionService {

    private final SysPermissionMapper sysPermissionMapper;

    public List<SysPermission> listAll() {
        return sysPermissionMapper.selectAll();
    }

    public List<SysPermission> listTree() {
        List<SysPermission> all = sysPermissionMapper.selectAll();
        List<SysPermission> roots = new ArrayList<>();
        for (SysPermission p : all) {
            if (p.getParentId() == null || p.getParentId() == 0) {
                roots.add(p);
                p.setChildren(findChildren(p.getId(), all));
            }
        }
        return roots;
    }

    private List<SysPermission> findChildren(Long parentId, List<SysPermission> all) {
        List<SysPermission> children = new ArrayList<>();
        for (SysPermission p : all) {
            if (p.getParentId() != null && p.getParentId().equals(parentId)) {
                children.add(p);
                p.setChildren(findChildren(p.getId(), all));
            }
        }
        return children;
    }
}
