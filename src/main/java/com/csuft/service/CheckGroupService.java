package com.csuft.service;

import com.csuft.common.PageResult;
import com.csuft.common.QueryPageBean;
import com.csuft.entity.CheckGroup;

public interface CheckGroupService {
    public PageResult findPage(QueryPageBean queryPageBean);

    public void addCheckGroup(CheckGroup checkGroup, Integer[] checkitemIds);
}
