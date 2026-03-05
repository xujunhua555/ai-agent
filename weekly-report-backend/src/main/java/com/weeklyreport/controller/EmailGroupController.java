package com.weeklyreport.controller;

import com.weeklyreport.controller.vo.Result;
import com.weeklyreport.entity.EmailGroup;
import com.weeklyreport.service.EmailGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/email/group")
@RequiredArgsConstructor
public class EmailGroupController {

    private final EmailGroupService emailGroupService;

    @GetMapping("/list")
    public Result<List<EmailGroup>> list() {
        return Result.ok(emailGroupService.listAll());
    }

    @GetMapping("/{id}")
    public Result<EmailGroup> get(@PathVariable Long id) {
        return Result.ok(emailGroupService.getById(id));
    }

    @PostMapping("/save")
    public Result<EmailGroup> save(@RequestBody EmailGroup group) {
        return Result.ok(emailGroupService.save(group));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        emailGroupService.delete(id);
        return Result.ok(null);
    }
}
