package com.csuft.service;

import com.csuft.common.PageResult;
import com.csuft.common.QueryPageBean;
import com.csuft.entity.CheckItem;

import java.util.List;

public interface CheckItemService {
    public List<CheckItem> findList();

    public void addCheckItem(CheckItem checkItem);

    public PageResult findPage(QueryPageBean queryPageBean);

    public CheckItem findById(Integer id);

    public void editCheckItem(CheckItem checkItem);

    public void delCheckItem(Integer id);
}
