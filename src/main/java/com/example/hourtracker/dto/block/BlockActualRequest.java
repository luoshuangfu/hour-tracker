package com.example.hourtracker.dto.block;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "填写时间块实际结果请求")
public class BlockActualRequest {

    @NotNull(message = "userId 不能为空")
    @Schema(description = "用户ID", example = "1")
    private Long userId;

    @NotNull(message = "date 不能为空")
    @Schema(description = "日期", example = "2026-04-12")
    private LocalDate date;

    @NotNull(message = "hour 不能为空")
    @Min(value = 0, message = "hour 必须在 0-23 之间")
    @Max(value = 23, message = "hour 必须在 0-23 之间")
    @Schema(description = "小时，范围 0-23", example = "9")
    private Integer hour;

    @NotBlank(message = "actualResult 不能为空")
    @Schema(description = "该小时的实际完成情况", example = "完成接口设计和表结构落库")
    private String actualResult;

    @Min(value = 1, message = "score 必须在 1-5 之间")
    @Max(value = 5, message = "score 必须在 1-5 之间")
    @Schema(description = "该时间块评分，范围 1-5", example = "4")
    private Integer score;

    @Schema(description = "该时间块复盘", example = "专注度较高，但联调时间预估偏少")
    private String review;
}
