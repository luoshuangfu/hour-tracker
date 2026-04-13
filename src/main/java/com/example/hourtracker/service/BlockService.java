package com.example.hourtracker.service;

import com.example.hourtracker.dto.block.BlockActualRequest;
import com.example.hourtracker.dto.block.BlockExpectedRequest;
import com.example.hourtracker.entity.Block;

import java.time.LocalDate;
import java.util.List;

public interface BlockService {

    Block getOrCreateBlock(Long userId, LocalDate date, Integer hour);

    Block updateExpected(BlockExpectedRequest request);

    Block updateActual(BlockActualRequest request);

    List<Block> listByUserAndDate(Long userId, LocalDate date);
}
