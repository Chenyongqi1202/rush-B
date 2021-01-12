package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 添加套餐和对应的检查组
     * @param setmealId
     * @param checkgroupId
     */
    void addSetmealCheckGroup(@Param("setmealId")Integer setmealId,@Param("checkgroupId")Integer checkgroupId);


    /**
     * 查询检查组id集合进行数据回显
     * @param id 套餐id
     * @return 对应的检查组id集合
     */
    List<Integer> findCheckGroupIdsBySetmealId(int id);

    /**
     * 分页查询
     * @param queryString
     * @return
     */
    Page<Setmeal> findPage(String queryString);

    /**
     * 查询套餐信息
     * @param id
     * @return
     */
    Setmeal findById(int id);


    /**
     * 更新套餐信息
     * @param setmeal
     */
    void update(Setmeal setmeal);

    /**
     * 删除套餐和检查组的关系
     * @param id
     * @throws MyException
     */
    void deleteBySetmealId(Integer id);

    /**
     * 查询套餐是否被订单使用
     * @param id
     * @return
     */
    int findCountBySetmealId(int id);


    /**
     * 删除套餐和检查组的关系
     * @param id
     */
    void deleteById(int id);

    /**
     * 查询所有图片
     * @return
     */
    List<String> findImgs();

    /**
     * 查询套餐列表
     * @return
     */
    List<Setmeal> findAll();
}
