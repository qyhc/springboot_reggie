package com.mercurows.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mercurows.domain.Category;

/*
 * 它继承自 IService<Category>，这暗示了它可能定义了一些与 Category 类相关的业务方法
 * IService通常用于定义一些基本的业务操作，例如CRUD操作。
 */
public interface CategoryService extends IService<Category>{
    public void remove(Long id);
}
