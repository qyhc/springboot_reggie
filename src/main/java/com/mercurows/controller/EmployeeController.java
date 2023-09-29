package com.mercurows.controller;

import java.util.Date;
import io.micrometer.common.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
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
import com.mercurows.domain.Employee;
import com.mercurows.service.EmployeeService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        // 1、将页面提交的密码password进行MD5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // 2、根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        // 因为Username列设置了唯一键约束，因此可以使用getOne
        Employee emp = employeeService.getOne(queryWrapper);

        // 3、如果没有查询到则返回登陆失败结果
        if (emp == null) {
            return R.error("登陆失败");
        }

        // 4、密码比较，如果不一致则返回登录失败结果
        if (!emp.getPassword().equals(password)) {
            return R.error("登陆失败");
        }

        // 5、查看员工状态，如果位禁用状态则返回员工禁用结果
        if (emp.getStatus() == 0) {
            return R.error("账号已禁用");
        }

        // 6、登陆成功，将员工id存入Session并返回登陆成功结果
        request.getSession().setAttribute("employee", emp.getId());
        // request.getSession().setAttribute("employee", emp.getId() + "");

        return R.success(emp);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        // 清理session中保存的当前登录员工的id
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("新增员工，员工信息：{}", employee.toString());

        // 设置初始密码123456，需要进行md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        // employee.setCreateTime(new Date());
        // employee.setUpdateTime(new Date());

        // 获取当前登录用户的id
        Long empId = (Long) request.getSession().getAttribute("employee");

        // employee.setCreateUser(empId);
        // employee.setUpdateUser(empId);

        employeeService.save(employee);

        return R.success("新增员工成功");
    }

    /*
     * 员工信息分页查询
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        log.info("page = {},pageSize = {},name = {}", page, pageSize, name);

        // 构造分页构造器
        Page pageInfo = new Page<>(page, pageSize);

        // 构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        // 添加过滤条件
        // 这种格式的，不需要写if判断是否为空
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        // 添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        // 执行查询
        employeeService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    /*
     * 根据id修改员工信息
     */
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        log.info(employee.toString());
        // Long empId = (Long) request.getSession().getAttribute("employee");
        // employee.setUpdateTime(new Date());
        // employee.setUpdateUser(empId);

        Long id = Thread.currentThread().getId();
        log.info("线程id为：{}", id);

        employeeService.updateById(employee);

        return R.success("员工修改成功");
    }

    /*
     * 根据id查询员工信息
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        log.info("根据id查询员工信息");
        Employee employee = employeeService.getById(id);
        if(employee!=null)
            return R.success(employee);
        return R.error("没有查询到相关员工信息");
    }
}
