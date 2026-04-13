package com.example.hourtracker.dto.block;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "填写时间块预期目标请求")
public class BlockExpectedRequest {

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

    @NotBlank(message = "expectedGoal 不能为空")
    @Schema(description = "该小时的预期目标", example = "完成需求设计并输出接口清单")
    private String expectedGoal;
}
