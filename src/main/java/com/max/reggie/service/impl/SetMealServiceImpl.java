package com.max.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.max.reggie.dto.SetmealDto;
import com.max.reggie.entity.SetMeal;
import com.max.reggie.entity.SetMealDish;
import com.max.reggie.mapper.SetMealMapper;
import com.max.reggie.service.SetMealDishService;
import com.max.reggie.service.SetMealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetMealServiceImpl extends ServiceImpl<SetMealMapper, SetMeal> implements SetMealService {

    @Autowired
    private SetMealDishService setMealDishService;
    /*
   新增套餐，同时保持套餐和菜品的关联关系
    * @param null
    * @return
    * @author: Max
    * @data:   2022/10/26
    */
    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        //保存套餐的基本信息，操作setmeal，执行insert操作
        this.save(setmealDto);

        List<SetMealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        //保存套餐和菜品的关联信息，操作set_meal_dish表，执行insert操作
        setMealDishService.saveBatch(setmealDishes);

    }
}
