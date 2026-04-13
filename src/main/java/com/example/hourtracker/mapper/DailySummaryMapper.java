package com.example.hourtracker.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hourtracker.entity.DailySummary;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DailySummaryMapper extends BaseMapper<DailySummary> {
}
