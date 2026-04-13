package com.example.hourtracker.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.hourtracker.common.BusinessException;
import com.example.hourtracker.dto.summary.AiSummaryResult;
import com.example.hourtracker.entity.Block;
import com.example.hourtracker.entity.DailySummary;
import com.example.hourtracker.entity.enums.DailySummaryStatus;
import com.example.hourtracker.mapper.BlockMapper;
import com.example.hourtracker.mapper.DailySummaryMapper;
import com.example.hourtracker.service.DailySummaryService;
import com.example.hourtracker.service.ai.AiSummaryClient;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DailySummaryServiceImpl implements DailySummaryService {

    private final DailySummaryMapper dailySummaryMapper;
    private final BlockMapper blockMapper;
    private final AiSummaryClient aiSummaryClient;
    private final Clock clock;

    public DailySummaryServiceImpl(DailySummaryMapper dailySummaryMapper,
                                   BlockMapper blockMapper,
                                   AiSummaryClient aiSummaryClient,
                                   Clock clock) {
        this.dailySummaryMapper = dailySummaryMapper;
        this.blockMapper = blockMapper;
        this.aiSummaryClient = aiSummaryClient;
        this.clock = clock;
    }

    @Override
    @Transactional
    public DailySummary saveUserSummary(Long userId, LocalDate date, String userSummary) {
        DailySummary dailySummary = getOrCreateDailySummary(userId, date);
        dailySummary.setUserSummary(userSummary);
        dailySummary.setUpdatedAt(LocalDateTime.now(clock));
        dailySummaryMapper.updateById(dailySummary);
        return dailySummary;
    }

    @Override
    @Transactional
    public DailySummary getSummary(Long userId, LocalDate date) {
        return generateAiSummaryIfAbsent(userId, date);
    }

    @Override
    @Transactional
    public DailySummary generateAiSummaryIfAbsent(Long userId, LocalDate date) {
        DailySummary dailySummary = getOrCreateDailySummary(userId, date);
        if (dailySummary.getStatus() == DailySummaryStatus.GENERATED.getCode()
                && hasText(dailySummary.getAiSummary())
                && hasText(dailySummary.getAiProblem())
                && hasText(dailySummary.getAiSuggestion())) {
            return dailySummary;
        }

        List<Block> blocks = listBlocks(userId, date);
        if (blocks.isEmpty()) {
            throw new BusinessException("当前日期没有 block 数据，无法生成 AI 总结");
        }

        AiSummaryResult aiSummaryResult = aiSummaryClient.generate(userId, date, blocks, dailySummary);
        dailySummary.setAiSummary(aiSummaryResult.getSummary());
        dailySummary.setAiProblem(aiSummaryResult.getProblem());
        dailySummary.setAiSuggestion(aiSummaryResult.getSuggestion());
        dailySummary.setStatus(DailySummaryStatus.GENERATED.getCode());
        dailySummary.setUpdatedAt(LocalDateTime.now(clock));
        dailySummaryMapper.updateById(dailySummary);
        return dailySummary;
    }

    @Override
    @Transactional
    public void autoGenerateYesterdaySummary() {
        LocalDate yesterday = LocalDate.now(clock).minusDays(1);
        List<Long> userIds = blockMapper.findDistinctUserIdsByDate(yesterday);
        for (Long userId : userIds) {
            try {
                generateAiSummaryIfAbsent(userId, yesterday);
            } catch (BusinessException ex) {
                // 没有可用 block 或其他业务限制时，跳过当前用户。
            }
        }
    }

    private DailySummary getOrCreateDailySummary(Long userId, LocalDate date) {
        DailySummary existing = findOne(userId, date);
        if (existing != null) {
            return existing;
        }

        LocalDateTime now = LocalDateTime.now(clock);
        DailySummary dailySummary = new DailySummary();
        dailySummary.setUserId(userId);
        dailySummary.setDate(date);
        dailySummary.setStatus(DailySummaryStatus.INIT.getCode());
        dailySummary.setCreatedAt(now);
        dailySummary.setUpdatedAt(now);
        try {
            dailySummaryMapper.insert(dailySummary);
            return dailySummary;
        } catch (DuplicateKeyException ex) {
            DailySummary duplicated = findOne(userId, date);
            if (duplicated != null) {
                return duplicated;
            }
            throw ex;
        }
    }

    private DailySummary findOne(Long userId, LocalDate date) {
        return dailySummaryMapper.selectOne(new LambdaQueryWrapper<DailySummary>()
                .eq(DailySummary::getUserId, userId)
                .eq(DailySummary::getDate, date)
                .last("LIMIT 1"));
    }

    private List<Block> listBlocks(Long userId, LocalDate date) {
        return blockMapper.selectList(new LambdaQueryWrapper<Block>()
                .eq(Block::getUserId, userId)
                .eq(Block::getDate, date)
                .orderByAsc(Block::getHour));
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}
