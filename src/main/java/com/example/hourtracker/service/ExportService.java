package com.example.hourtracker.service;

import java.time.LocalDate;

public interface ExportService {

    String exportDailyMarkdown(Long userId, LocalDate date);
}
