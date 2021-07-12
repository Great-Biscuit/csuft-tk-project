package com.csuft.mapper;


import com.csuft.entity.Setmeal;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 套餐功能mapper接口
 */
@Mapper
public interface SetMealMapper {
    //套餐数据的分页查询
    Page<Setmeal> findPage(@Param("queryString") String queryString);
    //查询套餐数据是否有关联
    Long findSetMealIds(Integer id);
    //删除关联数据
    void delSetMeal(Integer id);
    //保存套餐数据
    void addSetmeal(Setmeal setmeal);
    //添加套餐数据的关联关系
    void addSetmealAndCheckGroupId(Map<String, Integer> map);

    Setmeal findSetMeal(Integer id);

    List<Integer> findCheckGroupId(Integer id);

    void editSetMeal(Setmeal setmeal);

    void delSetMealAndCheckGroup(Integer id);
}
