package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Setmeal;

import java.util.List;

/**
 * com.itheima.health.service
 *
 * @Author: Chen
 * @Date: 2021/1/9 20:18
 */
public interface SetmealService {

    /**
     * 添加套餐
     * @param setmeal 套餐信息
     * @param checkgroupIds 检查组id数组
     */
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 查询检查组id集合进行数据回显
     * @param id 套餐id
     * @return 对应的检查组id集合
     */
    List<Integer> findCheckGroupIdsBySetmealId(int id);


    /**
     * 分页查询套餐信息
     * @param queryPageBean
     * @return
     */
    PageResult<Setmeal> findPage(QueryPageBean queryPageBean);
}
