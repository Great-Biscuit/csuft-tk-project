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
    @Autowired
    private ReportMapper reportMapper;

    @Override
    public Map findMemberCount() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(calendar.MONTH,-12);
        List<String> months = new ArrayList<>();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy.MM");
        for(int i=0;i<12;i++){
            calendar.add(calendar.MONTH,1);
            months.add(sf.format(calendar.getTime()));
        }
        List<Integer> memberCount = new ArrayList<>();
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
        Map<String,Object> map = new HashMap<>();

        map.put("months",months);
        map.put("memberCount",memberCount);

        return map;
    }

    @Override
    public Map findSetmealCount() {

        List<Map<String,Object>> setmealCount =  reportMapper.findSetmealAndOrderCount();

        List<String> setmealNames = new ArrayList<>();

        for (Map map:setmealCount) {
            String name = (String)map.get("name");
            setmealNames.add(name);
        }
        Map<String,Object> map = new HashMap<>();

        map.put("setmealNames",setmealNames);
        map.put("setmealCount",setmealCount);
        return map;
    }
}
