package com.max.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.max.reggie.dto.SetmealDto;
import com.max.reggie.entity.SetMeal;

public interface SetMealService extends IService<SetMeal> {
    
    /* 
    新增套餐，同时保持套餐和菜品的关联关系
     * @param null
     * @return
     * @author: Max
     * @data:   2022/10/26
     */
    public void saveWithDish(SetmealDto setmealDto);
}
