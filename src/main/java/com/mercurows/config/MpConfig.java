package com.mercurows.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;

// 使拦截器能被扫描
@Configuration
public class MpConfig {

    @Bean
    public MybatisPlusInterceptor mpInterceptor() {
        // 创建mp拦截器
        MybatisPlusInterceptor mpInterceptor = new MybatisPlusInterceptor();
        // 往mp拦截器中添加分页拦截器(具体拦截器)
        mpInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        // 往mp拦截器中添加乐观锁拦截器(具体拦截器)
        mpInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return mpInterceptor;
    }

}
