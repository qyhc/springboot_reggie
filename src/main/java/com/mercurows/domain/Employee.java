package com.mercurows.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import lombok.Data;

@Data
public class Employee implements Serializable{
    private static final long serialVersionUID = 1L;
    private Long id;
    private String username;
    private String name;
    private String password;
    private String phone;
    private String sex;
    private String idNumber;
    private Integer status;

    @TableField(fill = FieldFill.INSERT) //插入时填充字段
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE) //插入和更新时填充字段
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT)//插入时填充字段
    private Long createUser;
    @TableField(fill = FieldFill.INSERT_UPDATE)//插入和更新时填充字段
    private Long updateUser;
}
