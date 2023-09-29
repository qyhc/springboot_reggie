package com.mercurows.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mercurows.domain.Employee;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee>{

}
