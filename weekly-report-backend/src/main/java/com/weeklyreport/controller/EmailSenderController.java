package com.weeklyreport.controller;

import com.weeklyreport.controller.vo.Result;
import com.weeklyreport.entity.EmailSender;
import com.weeklyreport.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/email/sender")
@RequiredArgsConstructor
public class EmailSenderController {

    private final EmailSenderService emailSenderService;

    @GetMapping("/list")
    public Result<List<EmailSender>> list() {
        return Result.ok(emailSenderService.listAll());
    }

    @GetMapping("/{id}")
    public Result<EmailSender> get(@PathVariable Long id) {
        return Result.ok(emailSenderService.getById(id));
    }

    @PostMapping("/save")
    public Result<EmailSender> save(@RequestBody EmailSender sender) {
        return Result.ok(emailSenderService.save(sender));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        emailSenderService.delete(id);
        return Result.ok(null);
    }
}
