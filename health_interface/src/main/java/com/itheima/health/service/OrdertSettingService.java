package com.itheima.health.service;

import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * com.itheima.health.service
 *
 * @Author: Chen
 * @Date: 2021/1/11 16:14
 */
public interface OrdertSettingService {
    /**
     * 批量导入预约设置
     * @param orderSettingList
     * @throws MyException
     */
    void addBactch(List<OrderSetting> orderSettingList)throws MyException;

    /**
     * 查询最大预约数和已预约数的集合
     * @param month
     * @return
     */
    List<Map<String, Integer>> getOrderSettingByMonth(String month);

    /**
     * 根据日期进行预约人数设置
     * @param orderSetting
     * @throws MyException
     */
    void editNumberByDate(OrderSetting orderSetting) throws MyException;
}
