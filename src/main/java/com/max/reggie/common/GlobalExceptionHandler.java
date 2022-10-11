package com.max.reggie.common;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.sql.SQLIntegrityConstraintViolationException;

//全局异常处理
                                //controller的类型
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    //异常处理方法
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> ExceptionHandler(SQLIntegrityConstraintViolationException exception){
        log.error(exception.getMessage());
        //判断当前异常信息时候包含Duplicate entry的信息（主键重复）
        if(exception.getMessage().contains("Duplicate entry")){
            String [] split = exception.getMessage().split(" ");
            String msg = split[2]+"已存在";
           return R.error(msg);
        }
        return R.error("未知错误");
    }

    //自定义异常处理方法
    @ExceptionHandler(CustomException.class)
    public R<String> CustomExceptionHandler(CustomException exception){
        log.error(exception.getMessage());
        return R.error(exception.getMessage());//输出到页面上
    }


}
