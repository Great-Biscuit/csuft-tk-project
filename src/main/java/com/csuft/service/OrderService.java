package com.csuft.service;

import com.csuft.common.PageResult;
import com.csuft.common.QueryPageBean;
import com.csuft.entity.Order;

public interface OrderService {
    PageResult findPage(QueryPageBean queryPageBean);

    void editOrder(Order order);

    void delOrder(Integer id);
}
