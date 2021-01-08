package com.itheima.health.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * com.itheima.health.controller
 *
 * @Author: Chen
 * @Date: 2021/1/5 20:37
 */

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    /**
     * 订阅检查项服务
     */
    @Reference
    private CheckItemService checkItemService;

    @GetMapping("/findAll")
    public Result findAll(){
        List<CheckItem> checkItemList = checkItemService.findAll();
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemList);
    }

    @PostMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){

        checkItemService.add(checkItem);
        return new Result(true,MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<CheckItem> pageResult = checkItemService.findPage(queryPageBean);

        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,pageResult);
    }




}