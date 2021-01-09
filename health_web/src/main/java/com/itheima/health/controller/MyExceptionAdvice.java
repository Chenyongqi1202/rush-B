package com.itheima.health.controller;

import com.itheima.health.entity.Result;
import com.itheima.health.exception.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



/**
 * com.itheima.health.controller
 * log日志:
 *      info:记录流程性的内容,对重要业务时
 *      debug:记录重要的数据id,key
 *      error:记录异常信息 代替system.out,e.printStackTrace()
 * @Author: Chen
 * @Date: 2021/1/9 17:13
 */
@RestControllerAdvice
public class MyExceptionAdvice {

    private static final Logger log = LoggerFactory.getLogger(MyException.class);

    /**
     * 业务异常的处理
     * @param e
     * @return
     */
    @ExceptionHandler(MyException.class)
    public Result handleMyException(MyException e){
        return new Result(false,e.getMessage());
    }

    /**
     * 业务异常的处理
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        //记录异常信息\
        log.error("发生未知异常",e);
        return new Result(false,"发生未知异常,请联系管理员");
    }
}