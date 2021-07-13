package com.csuft.service;

import com.csuft.common.PageResult;
import com.csuft.common.QueryPageBean;
import com.csuft.entity.Setmeal;

import java.util.List;

public interface SetMealService {

    PageResult findPage(QueryPageBean queryPageBean);

    void delSetMeal(Integer id);

    void addSetmeal(Setmeal setmeal, Integer[] checkgroupIds);

    Setmeal findSetMeal(Integer id);

    List<Integer> findCheckGroupId(Integer id);

    void editSetMeal(Setmeal setmeal, Integer[] checkGroupIds);
}
