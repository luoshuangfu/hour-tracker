package com.example.hourtracker.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 用户表
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User {
    /**
     * 
     */
    @TableId
    private Integer id;

    /**
     * 用户昵称
     */
    private String user_name;

    /**
     * 头像
     */
    private String avatar_url;

    /**
     * 密码
     */
    private String password;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户状态
     */
    private Integer user_status;

    /**
     * 创建时间
     */
    private LocalDateTime create_time;

    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 是否会员
     */
    private Integer user_Plus;

    /**
     * 
     */
    private Integer user_id;
}