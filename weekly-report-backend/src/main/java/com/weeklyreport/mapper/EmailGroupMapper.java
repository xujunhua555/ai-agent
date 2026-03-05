package com.weeklyreport.mapper;

import com.weeklyreport.entity.EmailGroup;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmailGroupMapper {
    List<EmailGroup> selectAll();
    EmailGroup selectById(Long id);
    int insert(EmailGroup group);
    int updateById(EmailGroup group);
    int deleteById(Long id);
}
