package com.example.hourtracker.dto.export;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "导出 Markdown 响应")
public class ExportResponse {

    @Schema(description = "导出文件绝对路径", example = "G:/codexThread/项目/hour-tracker/exports/1/2026-04-12.md")
    private String filePath;
}
