package com.weeklyreport.mapper;

import com.weeklyreport.entity.EmailRecipient;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmailRecipientMapper {
    List<EmailRecipient> selectAll();
    EmailRecipient selectById(Long id);
    int insert(EmailRecipient recipient);
    int updateById(EmailRecipient recipient);
    int deleteById(Long id);
}
