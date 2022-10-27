package com.max.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.max.reggie.entity.AddressBook;
import com.max.reggie.entity.ShoppingCart;
import com.max.reggie.mapper.AddressBookMapper;
import com.max.reggie.mapper.ShoppingCartMapper;
import com.max.reggie.service.AddressBookService;
import com.max.reggie.service.SetMealService;
import com.max.reggie.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

}
