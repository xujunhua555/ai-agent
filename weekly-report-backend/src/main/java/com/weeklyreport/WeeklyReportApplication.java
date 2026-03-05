package com.weeklyreport;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.weeklyreport.mapper")
public class WeeklyReportApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeeklyReportApplication.class, args);
    }
}
