package com.example.hourtracker.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("block")
@Schema(description = "时间块实体")
public class Block {

    @TableId(type = IdType.AUTO)
    @Schema(description = "主键ID", example = "1")
    private Long id;

    @TableField("user_id")
    @Schema(description = "用户ID", example = "1")
    private Long userId;

    @Schema(description = "日期", example = "2026-04-12")
    private LocalDate date;

    @Schema(description = "小时，范围 0-23", example = "9")
    private Integer hour;

    @TableField("expected_goal")
    @Schema(description = "预期目标", example = "完成接口设计")
    private String expectedGoal;

    @TableField("actual_result")
    @Schema(description = "实际完成", example = "完成接口和数据库设计")
    private String actualResult;

    @Schema(description = "评分，范围 1-5", example = "4")
    private Integer score;

    @Schema(description = "复盘", example = "进度符合预期，但联调需要更早开始")
    private String review;

    @TableField("created_at")
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;
}
