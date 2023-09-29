package com.mercurows.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mercurows.common.CustomException;
import com.mercurows.domain.Setmeal;
import com.mercurows.domain.SetmealDish;
import com.mercurows.dto.SetmealDto;
import com.mercurows.mapper.SetmealMapper;
import com.mercurows.service.SetmealDishService;
import com.mercurows.service.SetmealService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    /*
     * 新增套餐 同时需要保存套餐和菜品的关联关系
     */
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        // 保存套餐的基本信息，操作setmeal，执行insert操作
        this.save(setmealDto);

        // 因为是新保存操作，setmealId为null
        // 所以需要人为的先将setmeal保存至数据库中然后才会生成id
        // 再进行赋值到SetmealDto的setmealId中
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        // 保存套餐和菜品的关联信息，操作setmeal_dish，执行insert操作
        setmealDishService.saveBatch(setmealDishes);
    }

    /*
     * 删除套餐，同时需要删除套餐和菜品的关联数据
     * @param id Setmeal的主键id
     */
    @Transactional
    public void removeWithDish(List<Long> ids) {
        // select count(*) from setmeal where id in (1,2,3) and status = 1 等价于下面四行代码
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId, ids);
        queryWrapper.eq(Setmeal::getStatus, 1);
        int count = (int) this.count(queryWrapper);

        if (count > 0) {
            // 如果不能删除，抛出一个业务异常
            throw new CustomException("套餐正在售卖中，不能删除");
        }
        // 如果可以删除，先删除套餐中的数据--setmeal
        this.removeByIds(ids);

        //删除关系表中的数据--setmeal_dish
        // delete from setmeal_dish where setmeal_id in (1,2,3)
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealDishService.remove(lambdaQueryWrapper);
    }
}
