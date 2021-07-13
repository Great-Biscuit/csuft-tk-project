package com.csuft;

import com.csuft.common.QueryPageBean;
import com.csuft.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

}
