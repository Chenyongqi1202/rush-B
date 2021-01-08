package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckGroup;

import java.util.List;

/**
 * com.itheima.health.controller
 *
 * @Author: Chen
 * @Date: 2021/1/8 16:11
 */
public interface CheckGroupService {
    /**
     * 添加检查组
     * @param checkGroup  检查组信息
     * @param checkitemIds 选择的检查项ID数组
     * @return
     */
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    /**
     * 分页查询
     * @param queryPageBean 分页条件
     * @return
     */
    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);

    /**
     * 查询检查组信息
     * @param id 检查组id
     * @return 检查组信息
     */
    CheckGroup findById(int id);

    /**
     * 根据检查组id查询所有检查项的id信息,数据回显
     * @param checkGroupId 检查组id
     * @return
     */
    List<Integer> findCheckItemIdsByCheckGroupId(int checkGroupId);

    /**
     * 修改检查组
     * @param checkGroup
     * @param checkitemIds
     */
    void update(CheckGroup checkGroup, Integer[] checkitemIds);
}
