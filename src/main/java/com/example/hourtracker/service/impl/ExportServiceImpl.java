package com.example.hourtracker.service.impl;

import com.example.hourtracker.entity.Block;
import com.example.hourtracker.entity.DailySummary;
import com.example.hourtracker.service.BlockService;
import com.example.hourtracker.service.DailySummaryService;
import com.example.hourtracker.service.ExportService;
import com.example.hourtracker.util.MarkdownExportUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

@Service
public class ExportServiceImpl implements ExportService {

    private final DailySummaryService dailySummaryService;
    private final BlockService blockService;
    private final MarkdownExportUtil markdownExportUtil;
    private final String exportDir;

    public ExportServiceImpl(DailySummaryService dailySummaryService,
                             BlockService blockService,
                             MarkdownExportUtil markdownExportUtil,
                             @Value("${app.export-dir:./exports}") String exportDir) {
        this.dailySummaryService = dailySummaryService;
        this.blockService = blockService;
        this.markdownExportUtil = markdownExportUtil;
        this.exportDir = exportDir;
    }

    @Override
    public String exportDailyMarkdown(Long userId, LocalDate date) {
        DailySummary dailySummary = dailySummaryService.generateAiSummaryIfAbsent(userId, date);
        List<Block> blocks = blockService.listByUserAndDate(userId, date);
        String markdown = markdownExportUtil.buildDailyMarkdown(userId, dailySummary, blocks);

        Path filePath = Path.of(exportDir, String.valueOf(userId), date + ".md");
        try {
            Files.createDirectories(filePath.getParent());
            Files.writeString(filePath, markdown, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            throw new IllegalStateException("导出 Markdown 失败: " + ex.getMessage(), ex);
        }
        return filePath.toAbsolutePath().toString();
    }
}
