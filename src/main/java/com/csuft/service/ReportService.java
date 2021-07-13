package com.csuft.service;

import java.util.Map;

public interface ReportService {
    //查询会员的数量
    Map findMemberCount();
    //查询套餐预约占比
    Map findSetmealCount();
}
