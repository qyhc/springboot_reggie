package com.mercurows.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mercurows.domain.Dish;
import com.mercurows.dto.DishDto;

public interface DishService extends IService<Dish> {
    // 新增菜品，同时擦汗如菜品对应的口味数据，需要操作两张表：dish、dish_flavor
    public void saveWithFlavor(DishDto dishDto);

    // 根据id查询菜品信息和对应的口味信息
    public DishDto getByWithFlavor(Long id);

    // 更新菜品信息，同时更新对应的口味信息
    public void updateWithFlavor(DishDto dishDto);
}
