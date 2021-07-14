package com.csuft.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReportMapper {
    //查询会员统计数量
    Integer findMemberCount(@Param("startTime") String startTime, @Param("endTime") String endTime);
    //统计套餐关联的订单数量
    List<Map<String, Object>> findSetmealAndOrderCount();
}
