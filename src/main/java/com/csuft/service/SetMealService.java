package com.csuft.service;

import com.csuft.common.PageResult;
import com.csuft.common.QueryPageBean;
import com.csuft.entity.Setmeal;

public interface SetMealService {

    PageResult findPage(QueryPageBean queryPageBean);
    void delSetMeal(Integer id);
    void addSetmeal(Setmeal setmeal, Integer[] checkgroupIds);
}
