package com.max.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.max.reggie.common.CustomException;
import com.max.reggie.entity.Category;
import com.max.reggie.entity.Dish;
import com.max.reggie.entity.SetMeal;
import com.max.reggie.mapper.CategoryMapper;
import com.max.reggie.service.CategoryService;
import com.max.reggie.service.DishService;
import com.max.reggie.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetMealService setMealService;
    /*
    根据id查询时候关联菜品，若没有则删除分类 
     * @return  
     * @author: Max
     * @data:   2022/10/11
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //查询当前分类是否关联菜品
        //添加查询条件，根据分类id进行查询
        lambdaQueryWrapper.eq(Dish::getCategoryId,id);//比较数据库中有多少条数据与前端传过来的id相同
        int count = dishService.count(lambdaQueryWrapper);
        if(count>0){//说明有菜品被关联，抛出一个业务异常
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }
        //查询当前分类是否关联套餐
        LambdaQueryWrapper<SetMeal> lambdaQueryWrapper2 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper2.eq(SetMeal::getCategoryId,id);
        int count2 = setMealService.count(lambdaQueryWrapper2);
        if(count2>0){//说明有套餐被关联，抛出一个业务异常
            throw new CustomException("当前套餐下关联了菜品，不能删除");
        }
        //正常删除分类
        super.removeById(id);
    }
}
