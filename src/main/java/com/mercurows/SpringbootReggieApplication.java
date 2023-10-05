package com.mercurows;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
// 添加注解，让其扫描含@WebFilter过滤器注解的类
@ServletComponentScan
// 开启事务控制
@EnableTransactionManagement
// 开启spring缓存注解功能
@EnableCaching
public class SpringbootReggieApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootReggieApplication.class, args);
		log.info("项目启动成功..");
	}
}
