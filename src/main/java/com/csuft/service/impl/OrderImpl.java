package com.csuft.service.impl;

import com.csuft.common.PageResult;
import com.csuft.common.QueryPageBean;
import com.csuft.entity.Order;
import com.csuft.mapper.OrderMapper;
import com.csuft.service.OrderService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class OrderImpl implements OrderService {
    @Autowired
    public OrderMapper orderMapper;

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<Map<String, Object>> page = orderMapper.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void editOrder(Order order) {
        orderMapper.editOrder(order);
    }

    @Override
    public void delOrder(Integer id) {
        orderMapper.delOrder(id);
    }
}
