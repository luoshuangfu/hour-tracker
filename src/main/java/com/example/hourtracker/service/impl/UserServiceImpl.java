package com.example.hourtracker.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hourtracker.entity.User;
import com.example.hourtracker.service.UserService;
import com.example.hourtracker.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 25054
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2026-04-13 12:34:02
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




