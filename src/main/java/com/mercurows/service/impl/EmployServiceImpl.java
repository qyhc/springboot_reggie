package com.mercurows.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mercurows.domain.Employee;
import com.mercurows.mapper.EmployeeMapper;
import com.mercurows.service.EmployeeService;

@Service
public class EmployServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements EmployeeService{

}
