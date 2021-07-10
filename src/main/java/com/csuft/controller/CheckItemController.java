package com.csuft.controller;

import com.csuft.common.MessageConst;
import com.csuft.common.PageResult;
import com.csuft.common.QueryPageBean;
import com.csuft.common.Result;
import com.csuft.entity.CheckItem;
import com.csuft.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checkItem")
public class CheckItemController {
    @Autowired
    CheckItemService checkItemService;

    @RequestMapping("/findList")
    public Result findList() {
        List<CheckItem> list = checkItemService.findList();
        if (list != null) {
            return new Result(true, MessageConst.QUERY_CHECKITEM_SUCCESS, list);
        } else {
            return new Result(false, MessageConst.QUERY_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult result = checkItemService.findPage(queryPageBean);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new PageResult(0L,null);
        }
    }

    @RequestMapping("/addCheckItem")
    public Result addCheckItem(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.addCheckItem(checkItem);
            return new Result(true, MessageConst.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.ADD_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            CheckItem checkItem = checkItemService.findById(id);
            return new Result(true, MessageConst.QUERY_CHECKITEM_SUCCESS, checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.QUERY_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("/editCheckItem")
    public Result editCheckItem(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.editCheckItem(checkItem);
            return new Result(true, MessageConst.EDIT_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.EDIT_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("/delCheckItem")
    public Result delCheckItem(Integer id) {
        try {
            checkItemService.delCheckItem(id);
            return new Result(true, MessageConst.DELETE_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.DELETE_CHECKITEM_FAIL);
        }
    }

}
