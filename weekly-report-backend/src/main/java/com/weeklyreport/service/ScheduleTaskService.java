package com.weeklyreport.service;

import com.weeklyreport.entity.ScheduleTask;
import com.weeklyreport.mapper.ScheduleTaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleTaskService {

    private final ScheduleTaskMapper scheduleTaskMapper;

    public List<ScheduleTask> listAll() {
        return scheduleTaskMapper.selectAll();
    }

    public ScheduleTask getById(Long id) {
        return scheduleTaskMapper.selectById(id);
    }

    public ScheduleTask save(ScheduleTask task) {
        if (task.getEnabled() == null) task.setEnabled(1);
        if (task.getId() == null) {
            scheduleTaskMapper.insert(task);
        } else {
            scheduleTaskMapper.updateById(task);
        }
        return task;
    }

    public void delete(Long id) {
        scheduleTaskMapper.deleteById(id);
    }
}
