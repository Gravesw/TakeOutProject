package com.max.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.max.reggie.entity.Orders;
import com.max.reggie.entity.User;
import com.max.reggie.mapper.OrderMapper;
import com.max.reggie.mapper.UserMapper;
import com.max.reggie.service.OrderService;
import com.max.reggie.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


}
