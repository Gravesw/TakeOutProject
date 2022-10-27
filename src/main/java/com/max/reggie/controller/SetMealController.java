package com.max.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.max.reggie.common.R;
import com.max.reggie.dto.DishDto;
import com.max.reggie.dto.SetmealDto;
import com.max.reggie.entity.Category;
import com.max.reggie.entity.Dish;
import com.max.reggie.entity.SetMeal;
import com.max.reggie.entity.SetMealDish;
import com.max.reggie.service.CategoryService;
import com.max.reggie.service.SetMealDishService;
import com.max.reggie.service.SetMealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("/setmeal")
@RestController
public class SetMealController {

    @Autowired
    private SetMealDishService setMealDishService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SetMealService setMealService;

    /*
    套餐页分页方法
     * @param page
     * @param pageSize
     * @return  com.max.reggie.common.R<com.baomidou.mybatisplus.extension.plugins.pagination.Page>
     * @author: Max
     * @data:   2022/10/26
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        //分页构造器对象
        Page<SetMeal> pageInfo = new Page<>(page,pageSize);
        Page<SetmealDto> dtoPage = new Page<>();

        LambdaQueryWrapper<SetMeal> queryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据name进行like模糊查询
        queryWrapper.like(name != null,SetMeal::getName,name);
        //添加排序条件，根据更新时间降序排列
        queryWrapper.orderByDesc(SetMeal::getUpdateTime);

        setMealService.page(pageInfo,queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
        List<SetMeal> records = pageInfo.getRecords();

        List<SetmealDto> list = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            //对象拷贝
            BeanUtils.copyProperties(item,setmealDto);
            //分类id
            Long categoryId = item.getCategoryId();
            //根据分类id查询分类对象
            Category category = categoryService.getById(categoryId);
            if(category != null){
                //分类名称
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }


    /*
    新增套餐方法
     * @param setmealDto
     * @return  com.max.reggie.common.R<java.lang.String>
     * @author: Max
     * @data:   2022/10/26
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        log.info("Json串的信息：",setmealDto);
        setMealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }
    
    
    
    /*
    批量停售、起售方法
     * @param status
     * @param ids 
     * @return  com.max.reggie.common.R<java.lang.String>
     * @author: Max
     * @data:   2022/10/26
     */
    @PostMapping("/status/{status}")
    public R<String> updateBatchStatus(@PathVariable long status, Long[] ids) {
        for (int i = 0; i < ids.length; i++) {
            SetMeal setMeal = setMealService.getById(ids[i]);
            setMeal.setStatus((int) status);
            setMealService.updateById(setMeal);
        }
        return R.success("状态修改成功！");
    }


    /* 
    批量删除方法
     * @param ids 
     * @return  com.max.reggie.common.R<java.lang.String>
     * @author: Max
     * @data:   2022/10/26
     */
    @DeleteMapping
    public R<String> deleteBatch(Long[] ids){
        for (int i = 0; i < ids.length; i++) {
            setMealService.removeById(ids[i]);
        }
        return R.success("删除成功");
    }

    /**
     * 根据条件查询套餐数据
     * @param setmeal
     * @return
     */
    @GetMapping("/list")
    public R<List<SetMeal>> list(SetMeal setmeal){
        LambdaQueryWrapper<SetMeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId() != null,SetMeal::getCategoryId,setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null,SetMeal::getStatus,setmeal.getStatus());
        queryWrapper.orderByDesc(SetMeal::getUpdateTime);

        List<SetMeal> list = setMealService.list(queryWrapper);

        return R.success(list);
    }
}
