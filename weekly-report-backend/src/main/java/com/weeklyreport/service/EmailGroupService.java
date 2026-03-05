package com.weeklyreport.service;

import com.weeklyreport.entity.EmailGroup;
import com.weeklyreport.mapper.EmailGroupMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailGroupService {

    private final EmailGroupMapper emailGroupMapper;

    public List<EmailGroup> listAll() {
        return emailGroupMapper.selectAll();
    }

    public EmailGroup getById(Long id) {
        return emailGroupMapper.selectById(id);
    }

    public EmailGroup save(EmailGroup group) {
        if (group.getId() == null) {
            emailGroupMapper.insert(group);
        } else {
            emailGroupMapper.update(group);
        }
        return group;
    }

    public void delete(Long id) {
        emailGroupMapper.deleteById(id);
    }
}
