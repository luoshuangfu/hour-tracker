package com.example.hourtracker.service;

import com.example.hourtracker.entity.DailySummary;

import java.time.LocalDate;

public interface DailySummaryService {

    DailySummary saveUserSummary(Long userId, LocalDate date, String userSummary);

    DailySummary getSummary(Long userId, LocalDate date);

    DailySummary generateAiSummaryIfAbsent(Long userId, LocalDate date);

    void autoGenerateYesterdaySummary();
}
