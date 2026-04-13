package com.example.hourtracker.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hourtracker.entity.Block;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface  BlockMapper extends BaseMapper<Block> {

    @Select("SELECT DISTINCT user_id FROM block WHERE date = #{date}")
    List<Long> findDistinctUserIdsByDate(LocalDate date);
}
