package com.csuft;

import com.csuft.common.QueryPageBean;
import com.csuft.entity.Order;
import com.csuft.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class OrderTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void orderPageTest() {
        QueryPageBean queryPageBean = new QueryPageBean();
        queryPageBean.setPageSize(100);
        queryPageBean.setCurrentPage(1);
        queryPageBean.setQueryString("");
        System.out.println(orderService.findPage(queryPageBean));
    }

    @Test
    public void updateOrderTest() {
        Order order = new Order();
        order.setId(27);
        order.setMemberId(98);
        order.setOrderDate(new Date());
        order.setOrderType("微信预约");
        order.setOrderStatus("未到诊");
        order.setSetmealId(12);
        orderService.editOrder(order);
    }

}
