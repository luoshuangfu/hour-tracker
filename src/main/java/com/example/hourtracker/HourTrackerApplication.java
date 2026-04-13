package com.example.hourtracker;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@MapperScan("com.example.hourtracker.mapper")
@SpringBootApplication
public class HourTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HourTrackerApplication.class, args);
    }
}
