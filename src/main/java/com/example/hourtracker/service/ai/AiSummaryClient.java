package com.example.hourtracker.service.ai;

import com.example.hourtracker.dto.summary.AiSummaryResult;
import com.example.hourtracker.entity.Block;
import com.example.hourtracker.entity.DailySummary;

import java.time.LocalDate;
import java.util.List;

public interface AiSummaryClient {

    AiSummaryResult generate(Long userId, LocalDate date, List<Block> blocks, DailySummary dailySummary);
}
