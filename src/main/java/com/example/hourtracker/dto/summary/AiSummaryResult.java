package com.example.hourtracker.dto.summary;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "AI 总结结果")
public class AiSummaryResult {

    @Schema(description = "AI 总结", example = "整体执行节奏较稳定，上午专注度高于下午")
    private String summary;
    @Schema(description = "AI 识别的问题", example = "部分时间块缺少实际结果，执行闭环不完整")
    private String problem;
    @Schema(description = "AI 给出的建议", example = "建议优先复盘低评分时间块，并把高效率时段固定下来")
    private String suggestion;
}
