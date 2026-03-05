package com.weeklyreport.service;

import com.weeklyreport.entity.EmailSender;
import com.weeklyreport.mapper.EmailSenderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final EmailSenderMapper emailSenderMapper;

    public List<EmailSender> listAll() {
        return emailSenderMapper.selectAll();
    }

    public EmailSender getById(Long id) {
        return emailSenderMapper.selectById(id);
    }

    public EmailSender save(EmailSender sender) {
        if (sender.getId() == null) {
            emailSenderMapper.insert(sender);
        } else {
            emailSenderMapper.update(sender);
        }
        return sender;
    }

    public void delete(Long id) {
        emailSenderMapper.deleteById(id);
    }
}
