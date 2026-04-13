package com.example.hourtracker.dto.export;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "导出 Markdown 请求")
public class ExportRequest {

    @NotNull(message = "userId 不能为空")
    @Schema(description = "用户ID", example = "1")
    private Long userId;

    @NotNull(message = "date 不能为空")
    @Schema(description = "日期", example = "2026-04-12")
    private LocalDate date;
}
