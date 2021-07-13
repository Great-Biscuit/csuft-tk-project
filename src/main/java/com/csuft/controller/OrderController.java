package com.csuft.controller;

import com.csuft.annotation.LoginRequired;
import com.csuft.common.MessageConst;
import com.csuft.common.PageResult;
import com.csuft.common.QueryPageBean;
import com.csuft.common.Result;
import com.csuft.entity.Order;
import com.csuft.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @LoginRequired
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult result = orderService.findPage(queryPageBean);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new PageResult(0L,null);
        }
    }

    @LoginRequired
    @RequestMapping("/editOrder")
    public Result editOrder(@RequestBody Order order) {
        try {
            orderService.editOrder(order);
            return new Result(true, MessageConst.EDIT_ORDER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.EDIT_ORDER_FAIL);
        }
    }

    @LoginRequired
    @RequestMapping("/delOrder")
    public Result delOrder(Integer id) {
        try {
            orderService.delOrder(id);
            return new Result(true, MessageConst.DELETE_ORDER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.DELETE_ORDER_FAIL);
        }
    }
}
