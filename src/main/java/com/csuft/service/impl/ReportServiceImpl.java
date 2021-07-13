package com.csuft.service.impl;

import com.csuft.mapper.ReportMapper;
import com.csuft.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 统计功能的实现
 */

@Service
public class ReportServiceImpl implements ReportService {

    //引入mapper的依赖
    @Autowired
    private ReportMapper reportMapper;

    @Override
    public Map findMemberCount() {
        //在数据返回的时候，要返回月份的集合，对应月份的会员数量
        //构建出来的月份就是2020.07
        Calendar calendar = Calendar.getInstance();
        //回到去年的7月份
        calendar.add(calendar.MONTH,-12);
        //定义一个用来存储日期的集合
        List<String> months = new ArrayList<>();
        //提供一个日期格式化的类
        SimpleDateFormat sf = new SimpleDateFormat("yyyy.MM");
        //循环那日期格式数据
        for(int i=0;i<12;i++){
            calendar.add(calendar.MONTH,1);
            months.add(sf.format(calendar.getTime()));
        }
        //2.根据循环的日期进行条件拼接，查询数据中的数据  具体拼接的 格式  regTime>=startTime and regTime<=endTime
        //定义返回数量的集合数据
        List<Integer> memberCount = new ArrayList<>();

        //循环每月的数据
        for (String month:months) {
            month= month.replace(".","-");
            String startTime = month+"-01";
            String endTime="";
            if("01,03,05,07,08,10,12".contains(month)) {
                endTime = month + "-31";
            }else if("04,06,09,11".contains(month)){
                endTime=month+"-30";
            }else{
                endTime=month+"-28";
            }
           Integer count =  reportMapper.findMemberCount(startTime,endTime);
            memberCount.add(count);
        }
        //进行数据返回的时候
        Map<String,Object> map = new HashMap<>();

        //月份
        map.put("months",months);
        //每月注册的会员数量
        map.put("memberCount",memberCount);

        return map;
    }

    @Override
    public Map findSetmealCount() {

        //将套餐的数据进行分类，并查询出来和订单关联的数据，要查询套餐关联订单的数量count
        List<Map<String,Object>> setmealCount =  reportMapper.findSetmealAndOrderCount();

        //对应的套餐分类名称
        List<String> setmealNames = new ArrayList<>();

        //循环查询出来的结果，拿到套餐名称
        for (Map map:setmealCount) {
            String name = (String)map.get("name");
            setmealNames.add(name);
        }
        //将封装好的数据装入map中
        Map<String,Object> map = new HashMap<>();

        map.put("setmealNames",setmealNames);
        map.put("setmealCount",setmealCount);
        return map;
    }
}
