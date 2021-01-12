package com.itheima.health.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.OrdertSettingDao;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrdertSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * com.itheima.health.service.impl
 *
 * @Author: Chen
 * @Date: 2021/1/11 16:46
 */
@Service(interfaceClass = OrdertSettingService.class)
public class OrdertSettingServiceImpl implements OrdertSettingService {

    @Autowired
    private OrdertSettingDao ordertSettingDao;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addBactch(List<OrderSetting> orderSettingList) {
        //1.判断集合中的数据不为空
        if (!CollectionUtils.isEmpty(orderSettingList)){
            //- 遍历导入的预约设置信息List<Ordersetting>
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (OrderSetting os : orderSettingList) {
                //2.查询是否有预约信息,没有则新增
                OrderSetting osInDB = ordertSettingDao.findByOrderDate(os.getOrderDate());
                if (osInDB == null){
                    ordertSettingDao.add(os);
                }else {
                    //3.查询是否有预约信息,有则进行更新
                    //3.1判断已预约人数是否大于要更新的最大预约数
                    if (osInDB.getReservations()>os.getNumber()){
                        //3.2 大于则报错
                        throw new MyException(sdf.format(os.getOrderDate()) + ": 最大预约数不能小于已预约人数");
                    }else {
                        //3.3 小于则进行更新
                        ordertSettingDao.updateNumber(os);
                    }
                }

            }

        }

    }

    @Override
    public List<Map<String, Integer>> getOrderSettingByMonth(String month) {
        month+="%";
        return ordertSettingDao.getOrderSettingByMonth(month);
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //1.查询是否有预约信息,没有则新增
        OrderSetting osInDB = ordertSettingDao.findByOrderDate(orderSetting.getOrderDate());
        if (osInDB == null){
            ordertSettingDao.add(orderSetting);
        }else {
            //3.查询是否有预约信息,有则进行更新
            //3.1判断已预约人数是否大于要更新的最大预约数
            if (osInDB.getReservations()>orderSetting.getNumber()){
                //3.2 大于则报错
                throw new MyException(sdf.format(orderSetting.getOrderDate()) + ": 最大预约数不能小于已预约人数");
            }else {
                //3.3 小于则进行更新
                ordertSettingDao.updateNumber(orderSetting);
            }
        }
    }
}