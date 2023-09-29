package com.mercurows.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mercurows.domain.SetmealDish;
import com.mercurows.mapper.SetmealDishMapper;
import com.mercurows.service.SetmealDishService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper,SetmealDish> implements SetmealDishService{
}
