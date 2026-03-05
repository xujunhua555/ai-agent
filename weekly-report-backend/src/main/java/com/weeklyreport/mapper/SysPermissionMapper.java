package com.weeklyreport.mapper;

import com.weeklyreport.entity.SysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysPermissionMapper {
    List<SysPermission> selectAll();

    List<SysPermission> selectByParentId(@Param("parentId") Long parentId);

    SysPermission selectById(@Param("id") Long id);

    List<SysPermission> selectByIds(@Param("ids") List<Long> ids);

    List<Long> selectPermissionIdsByRoleId(@Param("roleId") Long roleId);

    List<String> selectCodesByUserId(@Param("userId") Long userId);
}
