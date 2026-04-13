package com.example.hourtracker.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.hourtracker.common.BusinessException;
import com.example.hourtracker.dto.block.BlockActualRequest;
import com.example.hourtracker.dto.block.BlockExpectedRequest;
import com.example.hourtracker.entity.Block;
import com.example.hourtracker.mapper.BlockMapper;
import com.example.hourtracker.service.BlockService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BlockServiceImpl implements BlockService {

    private final BlockMapper blockMapper;
    private final Clock clock;

    public BlockServiceImpl(BlockMapper blockMapper, Clock clock) {
        this.blockMapper = blockMapper;
        this.clock = clock;
    }

    @Override
    @Transactional
    public Block getOrCreateBlock(Long userId, LocalDate date, Integer hour) {
        validateTodayOperation(date, hour);
        Block existing = findOne(userId, date, hour);
        if (existing != null) {
            return existing;
        }

        Block block = new Block();
        block.setUserId(userId);
        block.setDate(date);
        block.setHour(hour);
        LocalDateTime now = LocalDateTime.now(clock);
        block.setCreatedAt(now);
        block.setUpdatedAt(now);
        try {
            blockMapper.insert(block);
            return block;
        } catch (DuplicateKeyException ex) {
            return findRequired(userId, date, hour);
        }
    }

    @Override
    @Transactional
    public Block updateExpected(BlockExpectedRequest request) {
        validateTodayOperation(request.getDate(), request.getHour());
        LocalDateTime now = LocalDateTime.now(clock);
        LocalDateTime hourEnd = request.getDate().atTime(request.getHour(), 0).plusHours(1);
        if (now.isAfter(hourEnd)) {
            throw new BusinessException("当前时间已超过该小时结束时间，不能再修改 expected");
        }

        Block block = getOrCreateBlock(request.getUserId(), request.getDate(), request.getHour());
        block.setExpectedGoal(request.getExpectedGoal());
        block.setUpdatedAt(now);
        blockMapper.updateById(block);
        return block;
    }

    @Override
    @Transactional
    public Block updateActual(BlockActualRequest request) {
        validateTodayOperation(request.getDate(), request.getHour());
        LocalDateTime now = LocalDateTime.now(clock);
        LocalDateTime hourEnd = request.getDate().atTime(request.getHour(), 0).plusHours(1);
        if (!now.isAfter(hourEnd)) {
            throw new BusinessException("当前时间未超过该小时结束时间，不能填写 actual");
        }

        Block block = getOrCreateBlock(request.getUserId(), request.getDate(), request.getHour());
        block.setActualResult(request.getActualResult());
        block.setScore(request.getScore());
        block.setReview(request.getReview());
        block.setUpdatedAt(now);
        blockMapper.updateById(block);
        return block;
    }

    @Override
    public List<Block> listByUserAndDate(Long userId, LocalDate date) {
        return blockMapper.selectList(new LambdaQueryWrapper<Block>()
                .eq(Block::getUserId, userId)
                .eq(Block::getDate, date)
                .orderByAsc(Block::getHour));
    }

    private void validateTodayOperation(LocalDate date, Integer hour) {
        if (hour == null || hour < 0 || hour > 23) {
            throw new BusinessException("hour 必须在 0-23 之间");
        }
        LocalDate today = LocalDate.now(clock);
        if (!today.equals(date)) {
            throw new BusinessException("block 只能操作当天数据");
        }
    }

    private Block findRequired(Long userId, LocalDate date, Integer hour) {
        Block block = findOne(userId, date, hour);
        if (block == null) {
            throw new BusinessException("时间块不存在");
        }
        return block;
    }

    private Block findOne(Long userId, LocalDate date, Integer hour) {
        return blockMapper.selectOne(new LambdaQueryWrapper<Block>()
                .eq(Block::getUserId, userId)
                .eq(Block::getDate, date)
                .eq(Block::getHour, hour)
                .last("LIMIT 1"));
    }
}
