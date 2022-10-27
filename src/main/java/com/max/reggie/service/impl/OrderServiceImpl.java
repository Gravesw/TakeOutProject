package com.max.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.max.reggie.common.CustomException;
import com.max.reggie.entity.Category;
import com.max.reggie.entity.Dish;
import com.max.reggie.entity.Orders;
import com.max.reggie.entity.SetMeal;
import com.max.reggie.mapper.CategoryMapper;
import com.max.reggie.mapper.OrderMapper;
import com.max.reggie.service.CategoryService;
import com.max.reggie.service.DishService;
import com.max.reggie.service.OrderService;
import com.max.reggie.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {


}
