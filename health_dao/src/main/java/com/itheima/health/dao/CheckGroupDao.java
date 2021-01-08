package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * com.itheima.health.service
 *
 * @Author: Chen
 * @Date: 2021/1/8 16:24
 */
public interface CheckGroupDao {

    /**
     * 添加检查组
     * @param checkGroup 检查组信息
     */
    void add(CheckGroup checkGroup);

    /**
     * 添加检查组与检查项的关系
     * @param checkGroupId 检查组id
     * @param checkitemId 检查项id
     */
    void addCheckGroupCheckItem(@Param("checkGroupId") Integer checkGroupId,@Param("checkitemId") Integer checkitemId);

    /**
     * 分页查询
     * @param queryString 查询条件
     * @return
     */
    Page<CheckGroup> findByCondition(String queryString);

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
     * 更新检查组信息
     * @param checkGroup 检查组信息
     */
    void update(CheckGroup checkGroup);

    /**
     * 删除旧的检查组与检查项关联
     * @param checkGroupId
     */
    void delete(Integer checkGroupId);
}
