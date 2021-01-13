package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Order;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.QiNiuUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * com.itheima.health.controller
 *
 * @Author: Chen
 * @Date: 2021/1/12 19:40
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealMobileController {

    @Reference
    private SetmealService setmealService;

    /**
     * 套餐列表查询
     * @return
     */
    @GetMapping("/getSetmeal")
    public Result getSetmeal(){
        //调用服务来进行查询
        List<Setmeal> list = setmealService.findAll();
        //拼接图片的完整路径
        list.forEach(s->s.setImg(QiNiuUtils.DOMAIN +s.getImg()));

        return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS,list);
    }

    /**
     * 套餐页面详情展示
     * @param id
     * @return
     */
    @GetMapping("/findDetailById")
    public Result findDetailById(int id){
        //调用服务来进行查询
        Setmeal setmeal = setmealService.findDetailById(id);
        //拼接图片的完整路径
        setmeal.setImg(QiNiuUtils.DOMAIN +setmeal.getImg());

        return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS,setmeal);
    }
}