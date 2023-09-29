package com.mercurows.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mercurows.common.CustomException;
import com.mercurows.domain.Category;
import com.mercurows.domain.Dish;
import com.mercurows.domain.Setmeal;
import com.mercurows.mapper.CategoryMapper;
import com.mercurows.service.CategoryService;
import com.mercurows.service.DishService;
import com.mercurows.service.SetmealService;

/*
 * 该类使用了@Service注解，表明它是一个Spring的服务类（或称为服务层），负责处理业务逻辑。
 * 该类继承了ServiceImpl<CategoryMapper, Category>，这表明它是一个基于MyBatis-Plus的ServiceImpl，用于对Category类的数据库操作。
 * 通过继承ServiceImpl，CategoryServiceImpl将会具有对数据库的基本操作，例如增删改查。
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category> implements CategoryService{
    @Autowired
    private SetmealService setmealService;

    @Autowired
    private DishService dishService;

    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 添加查询条件，分局分类id查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count1 = (int) dishService.count(dishLambdaQueryWrapper);

        if (count1 > 0) {
            throw new CustomException("当前分类下关联了菜品，不能删除");
            // 查询当前分类是否关联了菜品，如果已经关联，抛出一个业务异常
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 添加查询条件，根据分类id进行查询
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int count2 = (int) setmealService.count(setmealLambdaQueryWrapper);

        if (count2 > 0) {
            throw new CustomException("当前分类下关联了套餐，不能删除");
            // 查询当前分类是否关联了套餐，如果已经关联，抛出一个业务异常
        }

        // 正常删除分类
        super.removeById(id);
    }
}
