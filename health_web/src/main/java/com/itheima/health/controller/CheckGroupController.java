package com.itheima.health.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * com.itheima.health.controller
 *
 * @Author: Chen
 * @Date: 2021/1/5 20:37
 */

@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

    /**
     * 订阅检查组服务
     */
    @Reference
    private CheckGroupService checkGroupService;

    /**
     * 添加检查组
     * @param checkGroup  检查组信息
     * @param checkitemIds 选择的检查项ID数组
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){

        //调用服务 添加检查组
        checkGroupService.add(checkGroup,checkitemIds);

        return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    /**
     * 分页查询
     * @param queryPageBean 分页条件
     * @return
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){

        //调用服务 分页查询检查组
        PageResult<CheckGroup> pageResult = checkGroupService.findPage(queryPageBean);
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,pageResult);
    }

    @GetMapping("/findById")
    public Result findById(int id){

        //调用服务 根据id查询检查组信息,数据回显
        CheckGroup checkGroup = checkGroupService.findById(id);
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
    }

    @GetMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(int id){
        //调用服务 根据检查组id查询所有检查项的id信息,数据回显
        List<Integer> checkItemIds = checkGroupService.findCheckItemIdsByCheckGroupId(id);
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkItemIds);
    }

    @PostMapping("/update")
    public Result update(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){

        //调用服务 修改检查组
        checkGroupService.update(checkGroup,checkitemIds);

        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }



}