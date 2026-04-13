package com.example.hourtracker.service.impl;

import com.example.hourtracker.dto.summary.AiSummaryResult;
import com.example.hourtracker.entity.Block;
import com.example.hourtracker.entity.DailySummary;
import com.example.hourtracker.service.ai.AiSummaryClient;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class MockAiSummaryClient implements AiSummaryClient {

    @Override
    public AiSummaryResult generate(Long userId, LocalDate date, List<Block> blocks, DailySummary dailySummary) {
        long completedCount = blocks.stream()
                .filter(block -> block.getActualResult() != null && !block.getActualResult().isBlank())
                .count();
        double avgScore = blocks.stream()
                .map(Block::getScore)
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
        List<Integer> missingHours = blocks.stream()
                .filter(block -> block.getActualResult() == null || block.getActualResult().isBlank())
                .map(Block::getHour)
                .sorted(Comparator.naturalOrder())
                .toList();

        String bestHours = blocks.stream()
                .filter(block -> block.getScore() != null && block.getScore() >= 4)
                .map(block -> block.getHour() + "点")
                .collect(Collectors.joining("、"));
        if (bestHours.isBlank()) {
            bestHours = "暂无高评分时间块";
        }

        String summary = String.format(
                "用户 %d 在 %s 共记录 %d 个时间块，已填写实际完成 %d 个，平均评分 %.1f。表现较好的时段：%s。",
                userId, date, blocks.size(), completedCount, avgScore, bestHours
        );

        String problem = missingHours.isEmpty()
                ? "所有已创建时间块都补充了实际完成，整体执行闭环较完整。"
                : "以下时间块还缺少实际完成记录：" + missingHours.stream()
                .map(hour -> hour + "点")
                .collect(Collectors.joining("、")) + "。";

        String suggestion = dailySummary.getUserSummary() == null || dailySummary.getUserSummary().isBlank()
                ? "建议在每日结束时补充 user_summary，并优先复盘低评分或未完成的时间块。"
                : "建议结合用户总结继续优化低评分时段，把高评分时段沉淀成固定工作节奏。";

        return new AiSummaryResult(summary, problem, suggestion);
    }
}
