package com.itheima.health.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.SetmealDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * com.itheima.health.service.impl
 *
 * @Author: Chen
 * @Date: 2021/1/9 20:21
 */
@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    @Override
    @Transactional
    public void add(Setmeal setmeal, Integer[] checkgroupIds){
        //添加套餐
        setmealDao.add(setmeal);
        //获取套餐的id
        Integer setmealId = setmeal.getId();
        //添加套餐和对应的检查组
        if (checkgroupIds !=null){
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealCheckGroup(setmealId,checkgroupId);
            }
        }
    }

    @Override
    public List<Integer> findCheckGroupIdsBySetmealId(int id) {
        List<Integer> checkGroupIds = setmealDao.findCheckGroupIdsBySetmealId(id);
        return checkGroupIds;
    }

    @Override
    public PageResult<Setmeal> findPage(QueryPageBean queryPageBean) {
        //CurrentPage:当前页数   PageSize:每页条数
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //查询条件
        if (StringUtils.isNotEmpty(queryPageBean.getQueryString())){
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        Page<Setmeal> page = setmealDao.findPage(queryPageBean.getQueryString());
        PageResult<Setmeal> pageResult = new PageResult<>(page.getTotal(),page.getResult());
        return pageResult;
    }

    @Override
    public Setmeal findById(int id) {
        Setmeal setmeal = setmealDao.findById(id);
        return setmeal;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Setmeal setmeal, Integer[] checkgroupIds) {
        //1.更新套餐信息
        setmealDao.update(setmeal);
        //2.删除套餐和检查组的关系
        setmealDao.deleteBySetmealId(setmeal.getId());
        //3.判断选中的检查组是否为空
        if (checkgroupIds != null){
            //3.1 遍历选中的检查组
            for (Integer checkgroupId : checkgroupIds) {
                //3.2 添加套餐和检查组的关系
                setmealDao.addSetmealCheckGroup(setmeal.getId(),checkgroupId);
            }
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(int id) {
        //1.判断套餐是否被订单使用
        int count = setmealDao.findCountBySetmealId(id);
        if (count > 0){
            throw new MyException("该套餐已被订单使用,不能删除");
        }
        //2.删除套餐与检查组的关系
        setmealDao.deleteBySetmealId(id);
        //3.删除套餐信息
        setmealDao.deleteById(id);
    }
}