package com.csuft.service.impl;

import com.csuft.entity.Order;
import com.csuft.mapper.OrderMapper;
import com.csuft.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class OrderImpl implements OrderService {
    @Autowired
    public OrderMapper orderMapper;

/*    @Override
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return orderMapper.findPage(queryPageBean);
    }*/

    @Override
    public void editOrder(Order order) {
        orderMapper.editOrder(order);
    }

    @Override
    public void delOrder(Integer id) {
        orderMapper.delOrder(id);
    }
}
