package com.csuft.controller;

import com.csuft.common.MessageConst;
import com.csuft.common.Result;
import com.csuft.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 统计模块功能实现
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    //注入相关的依赖
    @Autowired
    private ReportService reportService;

    //查询会员近1年的注册情况
    @RequestMapping("/findMemberCount")
    public Result findMemberCount(){
        try{
            Map map = reportService.findMemberCount();
            return new Result(true, MessageConst.QUERY_MEMBER_SUCCESS,map);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConst.QUERY_MEMBER_FAIL);
        }
    }
    //查询套餐的预约占比
    @RequestMapping("/findSetmealCount")
    public Result findSetmealCount(){
        try{
            Map map  =reportService.findSetmealCount();
            return new Result(true, MessageConst.QUERY_SETMEAL_ORDER_SUCCESS,map);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConst.QUERY_SETMEAL_OREDER_FAIL);
        }
    }
}
