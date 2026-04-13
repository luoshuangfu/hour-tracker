package com.example.hourtracker.dto.summary;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "用户填写每日总结请求")
public class UserSummaryRequest {

    @NotNull(message = "userId 不能为空")
    @Schema(description = "用户ID", example = "1")
    private Long userId;

    @NotNull(message = "date 不能为空")
    @Schema(description = "日期", example = "2026-04-12")
    private LocalDate date;

    @NotBlank(message = "userSummary 不能为空")
    @Schema(description = "用户每日总结", example = "今天上午效率高，下午被临时事项打断，需要优化时间预留")
    private String userSummary;
}
