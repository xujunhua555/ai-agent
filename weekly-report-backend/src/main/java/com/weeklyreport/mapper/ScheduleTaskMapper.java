package com.weeklyreport.mapper;

import com.weeklyreport.entity.ScheduleTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScheduleTaskMapper {
    List<ScheduleTask> selectAll();
    ScheduleTask selectById(Long id);
    int insert(ScheduleTask task);
    int updateById(ScheduleTask task);
    int deleteById(Long id);
}
