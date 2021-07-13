package com.csuft.mapper;

import com.csuft.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    //public PageResult findPage(@RequestBody QueryPageBean queryPageBean);

    public void editOrder(Order order);

    public void delOrder(Integer id);
}
