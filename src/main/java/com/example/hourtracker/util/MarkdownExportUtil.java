package com.example.hourtracker.util;

import com.example.hourtracker.entity.Block;
import com.example.hourtracker.entity.DailySummary;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MarkdownExportUtil {

    public String buildDailyMarkdown(Long userId, DailySummary dailySummary, List<Block> blocks) {
        StringBuilder builder = new StringBuilder();
        builder.append("# 时间块行为记录\n\n");
        builder.append("- 用户ID：").append(userId).append('\n');
        builder.append("- 日期：").append(dailySummary.getDate()).append("\n\n");

        builder.append("## 24小时 Block 数据\n\n");
        builder.append("| 小时 | 预期目标 | 实际完成 | 评分 | 复盘 |\n");
        builder.append("| --- | --- | --- | --- | --- |\n");

        for (int hour = 0; hour < 24; hour++) {
            Block block = findByHour(blocks, hour);
            builder.append("| ")
                    .append(hour)
                    .append(" | ")
                    .append(safe(block == null ? null : block.getExpectedGoal()))
                    .append(" | ")
                    .append(safe(block == null ? null : block.getActualResult()))
                    .append(" | ")
                    .append(safe(block == null || block.getScore() == null ? null : String.valueOf(block.getScore())))
                    .append(" | ")
                    .append(safe(block == null ? null : block.getReview()))
                    .append(" |\n");
        }

        builder.append("\n## AI 总结\n\n");
        builder.append("- 总结：").append(safe(dailySummary.getAiSummary())).append('\n');
        builder.append("- 问题：").append(safe(dailySummary.getAiProblem())).append('\n');
        builder.append("- 建议：").append(safe(dailySummary.getAiSuggestion())).append("\n\n");

        builder.append("## 用户总结\n\n");
        builder.append(safe(dailySummary.getUserSummary())).append('\n');
        return builder.toString();
    }

    private Block findByHour(List<Block> blocks, int hour) {
        return blocks.stream()
                .filter(item -> item.getHour() != null && item.getHour() == hour)
                .findFirst()
                .orElse(null);
    }

    private String safe(String value) {
        return value == null || value.isBlank() ? "-" : value.replace("\n", "<br/>");
    }
}
