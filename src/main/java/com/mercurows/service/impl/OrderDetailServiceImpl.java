package com.mercurows.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mercurows.domain.OrderDetail;
import com.mercurows.mapper.OrderDetailMapper;
import com.mercurows.service.OrderDetailService;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper,OrderDetail> implements OrderDetailService{
}
