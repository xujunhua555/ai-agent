package com.weeklyreport.controller;

import com.weeklyreport.controller.vo.Result;
import com.weeklyreport.entity.ScheduleTask;
import com.weeklyreport.service.ScheduleTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule/task")
@RequiredArgsConstructor
public class ScheduleTaskController {

    private final ScheduleTaskService scheduleTaskService;

    @GetMapping("/list")
    public Result<List<ScheduleTask>> list() {
        return Result.ok(scheduleTaskService.listAll());
    }

    @GetMapping("/{id}")
    public Result<ScheduleTask> get(@PathVariable Long id) {
        return Result.ok(scheduleTaskService.getById(id));
    }

    @PostMapping("/save")
    public Result<ScheduleTask> save(@RequestBody ScheduleTask task) {
        return Result.ok(scheduleTaskService.save(task));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        scheduleTaskService.delete(id);
        return Result.ok(null);
    }
}
