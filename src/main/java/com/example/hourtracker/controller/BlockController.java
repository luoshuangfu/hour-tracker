package com.example.hourtracker.controller;

import com.example.hourtracker.common.ApiResponse;
import com.example.hourtracker.dto.block.BlockActualRequest;
import com.example.hourtracker.dto.block.BlockExpectedRequest;
import com.example.hourtracker.entity.Block;
import com.example.hourtracker.service.BlockService;
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
@RequestMapping("/block")
@Tag(name = "Block", description = "时间块管理接口")
public class BlockController {

    private final BlockService blockService;

    public BlockController(BlockService blockService) {
        this.blockService = blockService;
    }

    @GetMapping
    @Operation(summary = "获取或创建 block", description = "只能操作当天数据，若 block 不存在则自动创建")
    public ApiResponse<Block> getOrCreateBlock(@Parameter(description = "用户ID", example = "1") @RequestParam Long userId,
                                               @Parameter(description = "日期，只允许当天", example = "2026-04-12")
                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                               @Parameter(description = "小时，范围 0-23", example = "9") @RequestParam Integer hour) {
        return ApiResponse.success(blockService.getOrCreateBlock(userId, date, hour));
    }

    @PostMapping("/expected")
    @Operation(summary = "填写 expected", description = "只能在该小时结束前修改 expectedGoal")
    public ApiResponse<Block> updateExpected(@Valid @RequestBody BlockExpectedRequest request) {
        return ApiResponse.success(blockService.updateExpected(request), "expected 更新成功");
    }

    @PostMapping("/actual")
    @Operation(summary = "填写 actual", description = "只能在该小时结束后填写 actualResult、score、review")
    public ApiResponse<Block> updateActual(@Valid @RequestBody BlockActualRequest request) {
        return ApiResponse.success(blockService.updateActual(request), "actual 更新成功");
    }
}
