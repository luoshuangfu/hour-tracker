package com.example.hourtracker.scheduler;

import com.example.hourtracker.service.DailySummaryService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DailySummaryScheduler {

    private final DailySummaryService dailySummaryService;

    public DailySummaryScheduler(DailySummaryService dailySummaryService) {
        this.dailySummaryService = dailySummaryService;
    }

    @Scheduled(cron = "0 5 0 * * *", zone = "Asia/Shanghai")
    public void generateYesterdaySummary() {
        dailySummaryService.autoGenerateYesterdaySummary();
    }
}
