package com.itheima.health.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.dao.CheckGroupDao;
import com.itheima.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * com.itheima.health.service
 *
 * @Author: Chen
 * @Date: 2021/1/8 16:22
 */
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;
    @Override
    @Transactional
    public void add(CheckGroup checkGroup, Integer[] checkitemIds){
        //添加检查组信息
        checkGroupDao.add(checkGroup);
        //获得添加检查组后的自增ID
        Integer checkGroupId = checkGroup.getId();
        //遍历出选中的检查项id
        if (checkitemIds != null){
            for (Integer checkitemId : checkitemIds) {
                //添加检查组与检查项的关系
                checkGroupDao.addCheckGroupCheckItem(checkGroupId,checkitemId);
            }
        }

    }

    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        if (StringUtils.isNotEmpty(queryPageBean.getQueryString())){
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        Page<CheckGroup> page = checkGroupDao.findByCondition(queryPageBean.getQueryString());
        PageResult<CheckGroup> pageResult = new PageResult<CheckGroup>(page.getTotal(), page.getResult());
        return pageResult;
    }

    @Override
    public CheckGroup findById(int id) {
        CheckGroup checkGroup = checkGroupDao.findById(id);
        return checkGroup;
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(int checkGroupId) {

        List<Integer> checkGroupIds = checkGroupDao.findCheckItemIdsByCheckGroupId(checkGroupId);
        return checkGroupIds;
    }


    @Override
    @Transactional
    public void update(CheckGroup checkGroup, Integer[] checkitemIds) {
        //先更新检查组信息
        checkGroupDao.update(checkGroup);
        //删除检查组与检查项的关系
        checkGroupDao.deleteCheckGroupCheckItem(checkGroup.getId());
        //添加检查组与检查项的关系
        if (null !=checkitemIds){
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addCheckGroupCheckItem(checkGroup.getId(),checkitemId);
            }
        }
    }

    @Override
    public List<CheckGroup> findAll() {
        List<CheckGroup> checkGroupList = checkGroupDao.findAll();
        return checkGroupList;
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        int count = checkGroupDao.findCountByCheckGroupId(id);
        if (count > 0){
            throw new MyException("该检查组被套餐使用了,不能删除");
        }
        //删除检查组与检查项的关系
        checkGroupDao.deleteCheckGroupCheckItem(id);
        //删除检查组
        checkGroupDao.deleteById(id);
    }
}