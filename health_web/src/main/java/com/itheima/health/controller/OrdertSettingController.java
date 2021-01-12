package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrdertSettingService;
import com.itheima.health.utils.POIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * com.itheima.health.controller
 *
 * @Author: Chen
 * @Date: 2021/1/11 15:56
 */
@RestController
@RequestMapping("/ordersetting")
public class OrdertSettingController {

    private static final Logger log = LoggerFactory.getLogger(OrdertSettingController.class);

    @Reference
    private OrdertSettingService ordertSettingService;

    /**
     * 批量导入预约设置
     *
     * @param excelFile
     * @return
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile excelFile) {
        try {
            //解析Excel文件,调用POIUtils解析文件,得到List<String[]>
            //每一个数组就代表一行记录
            List<String[]> excelData = POIUtils.readExcel(excelFile);
            log.debug("导入预约设置读取到了{}条记录", excelData.size());
            //转成List<OrdertSetting>,再调用service方法做导入,返回给页面
            final SimpleDateFormat sdf = new SimpleDateFormat(POIUtils.DATE_FORMAT);
            List<OrderSetting> orderSettingList = excelData.stream().map(arr -> {
                OrderSetting os = new OrderSetting();
                try {
                    os.setOrderDate(sdf.parse(arr[0]));
                    os.setNumber(Integer.valueOf(arr[1]));
                } catch (ParseException e) {
                }
                return os;
            }).collect(Collectors.toList());
            //调用服务导入
            ordertSettingService.addBactch(orderSettingList);
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (IOException e) {
            log.error("导入预约设置失败", e);
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }

    @GetMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String month) {
        List<Map<String, Integer>> mapList = ordertSettingService.getOrderSettingByMonth(month);
        return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS,mapList);
    }

    @PostMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){

        ordertSettingService.editNumberByDate(orderSetting);
        return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
    }
}