package com.max.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.max.reggie.entity.AddressBook;
import com.max.reggie.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
}
