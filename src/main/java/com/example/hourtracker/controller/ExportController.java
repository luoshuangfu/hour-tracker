package com.example.hourtracker.controller;

import com.example.hourtracker.common.ApiResponse;
import com.example.hourtracker.dto.export.ExportRequest;
import com.example.hourtracker.dto.export.ExportResponse;
import com.example.hourtracker.service.ExportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/export")
@Tag(name = "Export", description = "导出接口")
public class ExportController {

    private final ExportService exportService;

    public ExportController(ExportService exportService) {
        this.exportService = exportService;
    }

    @PostMapping
    @Operation(summary = "导出 Markdown", description = "若 AI 总结不存在，则先生成 AI 总结，再导出 24 小时 block、AI 总结和用户总结")
    public ApiResponse<ExportResponse> export(@Valid @RequestBody ExportRequest request) {
        String filePath = exportService.exportDailyMarkdown(request.getUserId(), request.getDate());
        return ApiResponse.success(new ExportResponse(filePath), "Markdown 导出成功");
    }
}
