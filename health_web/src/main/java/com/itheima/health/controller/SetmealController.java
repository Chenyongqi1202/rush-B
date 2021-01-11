package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.QiNiuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * com.itheima.health.controller
 *
 * @Author: Chen
 * @Date: 2021/1/9 19:23
 */

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    private static final Logger log = LoggerFactory.getLogger(MyException.class);

    @Reference
    private SetmealService setmealService;

    /**
     * 上传图片
     *
     * @param imgFile
     * @return
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile imgFile) {
        //1.获取文件名
        String originalFilename = imgFile.getOriginalFilename();
        //2.截取后缀名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //3.生成唯一id
        String uniqueId = UUID.randomUUID().toString();
        //4.拼接唯一id
        String filename = uniqueId + suffix;

        //5.调用七牛工具上传图片
        try {
            /**
             * imgFile.getBytes() : 上传文件的字节数组
             * filename :文件名
             */
            QiNiuUtils.uploadViaByte(imgFile.getBytes(), filename);
            //6.构建返回的数据
        /*{
            imgName: 图片名 , 补全formData.img
            domain: 七牛的域名 图片回显imageUrl = domain+图片名
        }*/
            Map<String, String> map = new HashMap<String, String>();
            map.put("imgName", filename);
            map.put("domain", QiNiuUtils.DOMAIN);
            //7.放到result里,再返回给页面
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, map);
        } catch (IOException e) {
            log.error("上传文件失败", e);
            return new Result(true, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        setmealService.add(setmeal, checkgroupIds);
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }


    @GetMapping("/findCheckGroupIdsBySetmealId")
    public Result findCheckGroupIdsBySetmealId(int id) {
        List<Integer> checkGroupIds = setmealService.findCheckGroupIdsBySetmealId(id);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroupIds);
    }

    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult<Setmeal> pageResult = setmealService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS, pageResult);
    }


    @GetMapping("/findById")
    public Result findById(int id) {
        Setmeal setmeal = setmealService.findById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("setmeal", setmeal);
        map.put("domain", QiNiuUtils.DOMAIN);

        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, map);

    }

    @PostMapping("/update")
    public Result update(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        setmealService.update(setmeal, checkgroupIds);
        return new Result(true, MessageConstant.EDIT_SETMEAL_SUCCESS);
    }


    /**
     * 通过id删除套餐
     *
     * @param id
     * @return
     */
    @PostMapping("/deleteById")
    public Result deleteById(int id) {
        setmealService.deleteById(id);
        return new Result(true, "删除套餐成功");
    }

}