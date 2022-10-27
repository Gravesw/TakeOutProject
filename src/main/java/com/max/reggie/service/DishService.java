package com.max.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.max.reggie.dto.DishDto;
import com.max.reggie.entity.Dish;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


public interface DishService extends IService<Dish> {
    //新增菜品同时新增口味,需要同时操作两张表dish和dishFlavor
    public void saveWithFlavor(DishDto dishDto);

    //根据id查询菜品信息和口味信息
    public  DishDto getByIdWithFlavor(Long id);

    //更新菜品信息，同时更新口味信息
    public void updateWithFlavor(DishDto dishDto);
}
