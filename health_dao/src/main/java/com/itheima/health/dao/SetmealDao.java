package com.itheima.health.dao;

import com.itheima.health.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

/**
 * com.itheima.health.dao
 *
 * @Author: Chen
 * @Date: 2021/1/9 20:24
 */
public interface SetmealDao {

    /**
     * 添加套餐信息
     * @param setmeal
     */
    void add(Setmeal setmeal);

    void addSetmealCheckGroup(@Param("setmealId")Integer setmealId,@Param("checkgroupId")Integer checkgroupId);
}
