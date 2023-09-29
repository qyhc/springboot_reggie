package com.mercurows.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mercurows.domain.Setmeal;
import com.mercurows.dto.SetmealDto;

public interface SetmealService extends IService<Setmeal> {
    /*
     * 新增套餐 同时需要保存套餐和菜品的关联关系
     */
    public void saveWithDish(SetmealDto setmealDto);

    /*
     * 删除套餐，同时需要删除套餐和菜品的关联数据
     */
    public void removeWithDish(List<Long> ids);

}
