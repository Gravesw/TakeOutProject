package com.max.reggie.dto;

import com.max.reggie.entity.SetMeal;
import com.max.reggie.entity.SetMealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends SetMeal {

    private List<SetMealDish> setmealDishes;

    private String categoryName;
}
