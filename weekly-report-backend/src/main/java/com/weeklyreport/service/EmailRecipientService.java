package com.weeklyreport.service;

import com.weeklyreport.entity.EmailRecipient;
import com.weeklyreport.mapper.EmailRecipientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailRecipientService {

    private final EmailRecipientMapper emailRecipientMapper;

    public List<EmailRecipient> listByGroup(Long groupId) {
        return emailRecipientMapper.selectByGroup(groupId);
    }

    public EmailRecipient getById(Long id) {
        return emailRecipientMapper.selectById(id);
    }

    public EmailRecipient save(EmailRecipient recipient) {
        if (recipient.getId() == null) {
            emailRecipientMapper.insert(recipient);
        } else {
            emailRecipientMapper.update(recipient);
        }
        return recipient;
    }

    public void delete(Long id) {
        emailRecipientMapper.deleteById(id);
    }
}
