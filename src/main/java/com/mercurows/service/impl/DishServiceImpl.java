package com.mercurows.service.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mercurows.domain.Dish;
import com.mercurows.domain.DishFlavor;
import com.mercurows.dto.DishDto;
import com.mercurows.mapper.DishMapper;
import com.mercurows.service.DishFlavorService;
import com.mercurows.service.DishService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;

    // 因为涉及到多张表的操作所以要开启事务管理
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        // 由于前端传过来的数据DishDto中flavors没有dishId即dish表的主键
        // 所以要先将dish存入数据库中使其对应数据自动生成主键id
        // 之后通过getid方式即可获得方才生成的id

        // 保存菜品的基本信息到菜品表dish
        this.save(dishDto);
        // 获取菜品id
        Long dishId = dishDto.getId();

        // 菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();

        // 主键id回写 之前每个DishFlavor元素中的id
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        // 保存菜品口味数据到菜品口味表dish_flavor
        dishFlavorService.saveBatch(flavors);
    }

    /*
     * 根据id查询菜品信息和对应的口味信息
     */
    public DishDto getByWithFlavor(Long id) {
        // 查询菜品基本信息，从dish表查询
        Dish dish = this.getById(id);

        // 因为dish中没有口味信息，所以需要使用dishDto类
        DishDto dishDto = new DishDto();

        // 浅拷贝 将dish的信息拷贝到dishDto中（注意dishDto继承自dish）
        BeanUtils.copyProperties(dish, dishDto);

        // 查询当前菜品对应的口味信息，从dish_flavor表查询
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);

        return dishDto;
    }

    /*
     * 更新菜品信息，同时更新对应的口味信息
     */
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        // 更新dish表基本信息
        this.updateById(dishDto);

        // 清理当前菜品对应口味表数据--dish_flavor表的delete操作
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());

        dishFlavorService.remove(queryWrapper);
        // 添加当前提交过来的口味数据--dish_flavro表的insert操作
        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors = flavors.stream().map((item)->{
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }
}
