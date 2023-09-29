package com.mercurows.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mercurows.common.R;
import com.mercurows.domain.Category;
import com.mercurows.domain.Dish;
import com.mercurows.domain.DishFlavor;
import com.mercurows.dto.DishDto;
import com.mercurows.service.CategoryService;
import com.mercurows.service.DishFlavorService;
import com.mercurows.service.DishService;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DishFlavorService dishFlavorService;

    /*
     * 新增菜品
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        log.info(dishDto.toString());
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    /*
     * 菜品信息分页分页查询
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        // 构造分页构造器对象
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        // 条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();

        // 添加过滤条件
        queryWrapper.like(name != null, Dish::getName, name);
        // 添加排序条件
        queryWrapper.orderByDesc(Dish::getUpdateTime);

        // 执行分页查询
        dishService.page(pageInfo, queryWrapper);

        // 对象拷贝
        // 忽略拷贝records对象
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");

        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item, dishDto);

            Long categoryId = item.getCategoryId(); //分类id

            // 根据id查询分类对象
            Category category = categoryService.getById(categoryId);

            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }

    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id) {
        DishDto dishDto = dishService.getByWithFlavor(id);
        return R.success(dishDto);
    }

    /*
     * 修改菜品
     * @param dishDto
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        log.info(dishDto.toString());
        // dishService.saveWithFlavor(dishDto);
        dishService.updateWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    /*
     * 根据条件(前端传来的是id) 查询对应的菜品数据
     */
    // @GetMapping("/list")
    // public R<List<Dish>> list(Dish dish) {
    //     // 构造查询条件
    //     LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
    //     queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
    //     // 添加条件，查询状态为1(起售状态)的菜品
    //     queryWrapper.eq(Dish::getStatus, 1);
    //     // 添加排序条件
    //     queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
    //     List<Dish> list = dishService.list(queryWrapper);

    //     return R.success(list);
    // }

    /*
     * 根据条件(前端传来的是id) 查询对应的菜品数据
     */
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish) {
        // 构造查询条件
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        // 添加条件，查询状态为1(起售状态)的菜品
        queryWrapper.eq(Dish::getStatus, 1);
        // 添加排序条件
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = dishService.list(queryWrapper);

        List<DishDto> dishDtoList = list.stream().map((item) -> {
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item, dishDto);

            Long categoryId = item.getCategoryId(); //分类id

            // 根据id查询分类对象
            Category category = categoryService.getById(categoryId);

            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            // 当前菜品的id
            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DishFlavor::getDishId, dishId);
            // SQL:select * from dish_flavor where dish_id = ?
            List<DishFlavor> dishFlavorsList = dishFlavorService.list(lambdaQueryWrapper);
            dishDto.setFlavors(dishFlavorsList);

            return dishDto;
        }).collect(Collectors.toList());


        return R.success(dishDtoList);
    }
}
