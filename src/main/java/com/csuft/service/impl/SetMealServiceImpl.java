package com.csuft.service.impl;

import com.csuft.common.PageResult;
import com.csuft.common.QueryPageBean;
import com.csuft.entity.Setmeal;
import com.csuft.mapper.SetMealMapper;
import com.csuft.service.SetMealService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    private SetMealMapper setMealMapper;


    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Setmeal> page = setMealMapper.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void delSetMeal(Integer id) {
        Long count = setMealMapper.findSetMealIds(id);
        if(count>0){
            throw  new RuntimeException("当前记录有关联数据,请先消除关联数据");
        }
        setMealMapper.delSetMeal(id);
    }

    @Override
    public void addSetmeal(Setmeal setmeal, Integer[] checkgroupIds) {
        //1.保存套餐数据
        setMealMapper.addSetmeal(setmeal);
        //2.循环检查组的id来保存中间的表的数据
        for (Integer id:checkgroupIds) {
            Map<String,Integer> map = new HashMap();
            map.put("setmeal_id",setmeal.getId());
            map.put("checkgroup_id",id);

            setMealMapper.addSetmealAndCheckGroupId(map);
        }
    }
}
