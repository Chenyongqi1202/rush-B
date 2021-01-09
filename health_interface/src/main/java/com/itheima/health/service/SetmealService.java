package com.itheima.health.service;

import com.itheima.health.pojo.Setmeal;

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
}
