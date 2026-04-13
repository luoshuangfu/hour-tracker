package com.example.hourtracker.controller;

import com.example.hourtracker.common.ApiResponse;
import com.example.hourtracker.dto.summary.UserSummaryRequest;
import com.example.hourtracker.entity.DailySummary;
import com.example.hourtracker.service.DailySummaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Validated
@RestController
@RequestMapping("/summary")
@Tag(name = "Summary", description = "每日总结接口")
public class SummaryController {

    private final DailySummaryService dailySummaryService;

    public SummaryController(DailySummaryService dailySummaryService) {
        this.dailySummaryService = dailySummaryService;
    }

    @PostMapping("/user")
    @Operation(summary = "保存用户总结", description = "写入 user_summary 字段，不会实时计算 AI")
    public ApiResponse<DailySummary> saveUserSummary(@Valid @RequestBody UserSummaryRequest request) {
        DailySummary summary = dailySummaryService.saveUserSummary(
                request.getUserId(),
                request.getDate(),
                request.getUserSummary()
        );
        return ApiResponse.success(summary, "用户总结保存成功");
    }

    @GetMapping
    @Operation(summary = "获取每日总结", description = "若 AI 总结不存在，则先生成再返回")
    public ApiResponse<DailySummary> getSummary(@Parameter(description = "用户ID", example = "1") @RequestParam Long userId,
                                                @Parameter(description = "日期", example = "2026-04-12")
                                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ApiResponse.success(dailySummaryService.getSummary(userId, date));
    }
}
