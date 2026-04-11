package org.mahotao.hourtracker.service.lmpl;

import org.mahotao.hourtracker.controller.BlockController;
import org.mahotao.hourtracker.entity.Block;
import org.mahotao.hourtracker.mapper.BlockMapper;
import org.mahotao.hourtracker.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

public class BlockServiceImpl implements BlockService {
    @Autowired
    private BlockMapper blockMapper;

    //获取或创建
    public Block getOrCreate(int userId, LocalDate date,int hour) {

    }
}
