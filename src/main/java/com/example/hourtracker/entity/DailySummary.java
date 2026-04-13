package com.example.hourtracker.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.hourtracker.entity.enums.DailySummaryStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("daily_summary")
@Schema(description = "每日总结实体")
public class DailySummary {

    @TableId(type = IdType.AUTO)
    @Schema(description = "主键ID", example = "1")
    private Long id;

    @TableField("user_id")
    @Schema(description = "用户ID", example = "1")
    private Long userId;

    @Schema(description = "日期", example = "2026-04-12")
    private LocalDate date;

    @TableField("ai_summary")
    @Schema(description = "AI 总结")
    private String aiSummary;

    @TableField("ai_problem")
    @Schema(description = "AI 问题分析")
    private String aiProblem;

    @TableField("ai_suggestion")
    @Schema(description = "AI 建议")
    private String aiSuggestion;

    @TableField("user_summary")
    @Schema(description = "用户总结")
    private String userSummary;

    @TableField("status")
    @Schema(description = "状态")
    private int status;

    @TableField("created_at")
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;
}
