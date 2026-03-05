package com.weeklyreport.mapper;

import com.weeklyreport.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMapper {
    List<SysRole> selectAll();

    SysRole selectById(@Param("id") Long id);

    SysRole selectByCode(@Param("code") String code);

    int insert(SysRole role);

    int update(SysRole role);

    int deleteById(@Param("id") Long id);

    List<SysRole> selectByUserId(@Param("userId") Long userId);
}
