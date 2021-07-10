package com.csuft.service.impl;

import com.csuft.common.PageResult;
import com.csuft.common.QueryPageBean;
import com.csuft.entity.CheckItem;
import com.csuft.mapper.CheckItemMapper;
import com.csuft.service.CheckItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckItemImpl implements CheckItemService {
    @Autowired
    public CheckItemMapper checkItemMapper;

    @Override
    public List<CheckItem> findList() {
        return checkItemMapper.findList();
    }

    @Override
    public void addCheckItem(CheckItem checkItem) {
        checkItemMapper.addCheckItem(checkItem);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<CheckItem> page = checkItemMapper.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public CheckItem findById(Integer id) {
        return checkItemMapper.findById(id);
    }

    @Override
    public void editCheckItem(CheckItem checkItem) {
        checkItemMapper.editCheckItem(checkItem);
    }

    @Override
    public void delCheckItem(Integer id) {
        checkItemMapper.delCheckItem(id);
    }
}
