package com.weeklyreport.controller;

import com.weeklyreport.controller.vo.Result;
import com.weeklyreport.entity.EmailRecipient;
import com.weeklyreport.service.EmailRecipientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/email/recipient")
@RequiredArgsConstructor
public class EmailRecipientController {

    private final EmailRecipientService emailRecipientService;

    @GetMapping("/list")
    public Result<List<EmailRecipient>> list(@RequestParam(required = false) Long groupId) {
        return Result.ok(emailRecipientService.listByGroup(groupId));
    }

    @GetMapping("/{id}")
    public Result<EmailRecipient> get(@PathVariable Long id) {
        return Result.ok(emailRecipientService.getById(id));
    }

    @PostMapping("/save")
    public Result<EmailRecipient> save(@RequestBody EmailRecipient recipient) {
        return Result.ok(emailRecipientService.save(recipient));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        emailRecipientService.delete(id);
        return Result.ok(null);
    }
}
