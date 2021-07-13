package com.csuft.service;

import com.csuft.common.PageResult;
import com.csuft.common.QueryPageBean;
import com.csuft.entity.Order;

public interface OrderService {
    //public PageResult findPage(@RequestBody QueryPageBean queryPageBean);

    public void editOrder(Order order);

    public void delOrder(Integer id);
}
