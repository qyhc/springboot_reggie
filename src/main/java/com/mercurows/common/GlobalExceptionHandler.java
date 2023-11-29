package com.mercurows.common;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/*
 * 全局异常处理
 */
// 根据注解来捕获写了注解的类的异常
// 这个注解表明该类是一个全局控制器的通知类，
// 指定了在带有@RestController和@Controller注解的类中捕获的异常。
// @ControllerAdvice可以用于定义全局性的控制器建议，包括异常处理、绑定属性等。
@ControllerAdvice(annotations = { RestController.class,Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException exception) {
        log.error(exception.getMessage());
        // 是否是因为违反唯一性约束引起的异常
        if (exception.getMessage().contains("Duplicate entry")) {
            String[] split = exception.getMessage().split(" ");
            String msg = split[2] + "已存在";
            return R.error(msg);
        }
        return R.error("未知错误");
    }

    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandler(CustomException exception) {
        log.error(exception.getMessage());

        return R.error(exception.getMessage());
    }
}
