package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckItem;

import java.util.List;

/**
 * com.itheima.health.dao
 *
 * @Author: Chen
 * @Date: 2021/1/5 20:44
 */
public interface CheckItemDao {

    /**
     * 查询所有
     * @return
     */
    List<CheckItem> findAll();

    /**
     * 添加检查项
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 分页查询
     * @param queryString
     * @return
     */
    Page<CheckItem> findByCondition(String queryString);

    /**
     * 编辑检查项
     * @param checkItem
     */
    void update(CheckItem checkItem);

    /**
     * 根据id查询检查项
     * @param id 检查项id
     * @return
     */
    CheckItem findById(int id);

    /**
     * 统计被使用的检查项id
     * @param id
     * @return
     */
    int findCountByCheckItemID(int id);

    /**
     * 删除检查项
     * @param id
     */
    void delete(int id);

}