package com.weeklyreport.mapper;

import com.weeklyreport.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysUserRoleMapper {
    int insert(@Param("userId") Long userId, @Param("roleId") Long roleId);
    int deleteByUserId(@Param("userId") Long userId);
}
