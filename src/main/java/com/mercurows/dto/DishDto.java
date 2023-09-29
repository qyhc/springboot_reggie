package com.mercurows.dto;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

import com.mercurows.domain.Dish;
import com.mercurows.domain.DishFlavor;

// 用于表现层与服务层之间数据传输的辅助类
@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
