package com.mercurows.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mercurows.domain.OrderDetail;

@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail>{
}
