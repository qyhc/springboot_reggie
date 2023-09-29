package com.mercurows.common;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MyMetaObjecthandler implements MetaObjectHandler {
    /*
     * 插入操作自动填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段填充[insert]...");

        System.out.println(metaObject.getOriginalObject());
        int index = metaObject.getOriginalObject().toString().indexOf('(');
        if (index != -1) {
            String slicedString = metaObject.getOriginalObject().toString().substring(0, index);
            if (slicedString.equals("ShoppingCart")) {
                System.out.println("Y");
                metaObject.setValue("createTime", new Date());
            } else {
                metaObject.setValue("createTime", new Date());
                metaObject.setValue("updateTime", new Date());
                metaObject.setValue("createUser", BaseContext.getCurrentId());
                metaObject.setValue("updateUser", BaseContext.getCurrentId());
            }
        }
    }

    /*
     * 更新操作自动填充
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段填充[update]...");
        log.info(metaObject.toString());

        Long id = Thread.currentThread().getId();
        log.info("线程id为：{}", id);

        metaObject.setValue("updateTime", new Date());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());
    }

}
