package com.example.hourtracker.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserStatus {
    STAY(1,"正常"),
    DELETED(0,"已注销");
    // 标记此字段为存入数据库的值
    @EnumValue
    private final int code;

    // 标记此字段为前端展示/JSON序列化的值
    @JsonValue
    private final String description;

    UserStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
