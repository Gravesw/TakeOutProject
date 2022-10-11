package com.max.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.max.reggie.common.R;
import com.max.reggie.entity.Category;
import com.max.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
        @Autowired
        private CategoryService categoryService;
        /*
        新增分类
         * @param category
         * @return  R<String>
         * @author: Max
         * @data:   2022/10/10
         */
        @PostMapping
        public R<String> save(@RequestBody Category category){
                log.info("category:{}",category);
                categoryService.save(category);
                return R.success("新增分类成功");
        }

        /*
        分页查询
         * @Param int page, int pageSize
         * @return  R.success(pageInfo)
         * @author: Max
         * @data:   2022/10/11
         */
        @GetMapping("/page")
        public R<Page> page(int page, int pageSize){
                System.out.println(page);
                System.out.println(pageSize);
                //分页构造器
                Page<Category> pageInfo = new Page<>(page,pageSize);
                //条件构造器
                LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                //添加排序条件,根据sort来排序
                lambdaQueryWrapper.orderByAsc(Category::getSort);
                //进行分页查询
                categoryService.page(pageInfo,lambdaQueryWrapper);
                return R.success(pageInfo);
        }

        /* 
        删除类别方法:根据id删除分类
         * @Param Long id
         * @return  R<String>
         * @author: Max
         * @data:   2022/10/11
         */
        @DeleteMapping
        public R<String> deleteCategory(Long ids){
                log.info("删除分类，id为{}",ids);
                //查询分类是否关联菜品
//                categoryService.removeById(ids);
                categoryService.remove(ids);
                return R.success("分类信息删除成功");
        }
}
