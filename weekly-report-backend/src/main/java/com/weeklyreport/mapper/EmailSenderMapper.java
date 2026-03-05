package com.weeklyreport.mapper;

import com.weeklyreport.entity.EmailSender;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmailSenderMapper {
    List<EmailSender> selectAll();

    EmailSender selectById(Long id);

    int insert(EmailSender sender);

    int updateById(EmailSender sender);

    int deleteById(Long id);
}
