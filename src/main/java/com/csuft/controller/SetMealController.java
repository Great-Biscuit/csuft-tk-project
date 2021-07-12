package com.csuft.controller;

import com.csuft.annotation.LoginRequired;
import com.csuft.common.*;
import com.csuft.entity.Setmeal;
import com.csuft.service.SetMealService;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * 套餐模块功能实现
 */
@RestController
@RequestMapping("/setmeal")
public class SetMealController {

    @Autowired
    private SetMealService setMealService;

    @LoginRequired
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        try{
            PageResult pageResult = setMealService.findPage(queryPageBean);
            return pageResult;
        }catch (Exception e){
            e.printStackTrace();
            return new PageResult(0L,null);
        }

    }

    @LoginRequired
    @RequestMapping("/delSetMeal")
    public Result delSetMeal(Integer id){
        try {
            setMealService.delSetMeal(id);
            return new Result(true,MessageConst.DELETE_SETMEAL_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            if(e.getMessage().contains("关联数据")){
                return new Result(false,e.getMessage());
            }else{
                return new Result(false, MessageConst.DELETE_SETMEAL_FAIL);
            }
        }
    }

    //图片上传的方法
    @LoginRequired
    @RequestMapping("/upload")
    public Result upload(@Param("imgFile") MultipartFile imgFile){
        try{
            //1.获取上传图片的名称
            String oldImg =  imgFile.getOriginalFilename();
            //2.改造当前名称，用来防止图片名称的重复
            String newImgUrl = UUID.randomUUID().toString().replace("-","")+oldImg;
            //3.调用工具类上传图片
            QiniuUtils.upload2QiNiu(imgFile.getBytes(),newImgUrl);
            //4.手动的拼接文件访问的路径
            String fileUrl = QiniuUtils.qiniu_img_url_pre+newImgUrl;
            //5.将图片返回的路径和上传结果进行返回
            return new Result(true,MessageConst.PIC_UPLOAD_SUCCESS,fileUrl);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConst.PIC_UPLOAD_FAIL);
        }
    }
    //添加套餐数据
    @LoginRequired
    @RequestMapping("/addSetmeal")
    public Result addSetmeal(@RequestBody Setmeal setmeal, Integer[] checkgroupIds){
        try{
            setMealService.addSetmeal(setmeal,checkgroupIds);
            return new Result(true,MessageConst.ADD_SETMEAL_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConst.ADD_SETMEAL_FAIL);
        }
    }

    // 根据套餐 ID 查询套餐对象
    @LoginRequired
    @RequestMapping("/findSetmeal")
    public Result findSetmeal(Integer id) {
        try {
            Setmeal res = setMealService.findSetMeal(id);
            return new Result(true, MessageConst.QUERY_SETMEAL_SUCCESS, res);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.QUERY_SETMEAL_FAIL);
        }
    }

    // 查询套餐关联的检查组ID
    @LoginRequired
    @RequestMapping("/findCheckGroupId")
    public Result findCheckGroupId(Integer id) {
        try {
            List<Integer> list = setMealService.findCheckGroupId(id);
            return new Result(true, MessageConst.QUERY_SETMEAL_CHECKGROUP_ID_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.QUERY_SETMEAL_CHECKGROUP_ID_FIAL);
        }
    }

    // 套餐数据编辑功能实现
    @LoginRequired
    @RequestMapping("/editSetMeal")
    public Result editSetMeal(@RequestBody Setmeal setmeal, Integer[] checkGroupIds) {
        try {
            setMealService.editSetMeal(setmeal, checkGroupIds);
            return new Result(true, MessageConst.EDIT_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.EDIT_SETMEAL_FAIL);
        }
    }
}
