package com.mercurows.dto;

import lombok.Data;
import java.util.List;

import com.mercurows.domain.Setmeal;
import com.mercurows.domain.SetmealDish;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
