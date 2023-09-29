package com.mercurows.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mercurows.domain.DishFlavor;
import com.mercurows.mapper.DishFlavorMapper;
import com.mercurows.service.DishFlavorService;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper,DishFlavor> implements DishFlavorService{
}
