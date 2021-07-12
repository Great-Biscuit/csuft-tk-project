package com.csuft.service;

import com.csuft.common.PageResult;
import com.csuft.common.QueryPageBean;
import com.csuft.entity.CheckGroup;

import java.util.List;

public interface CheckGroupService {
    public PageResult findPage(QueryPageBean queryPageBean);
    List<CheckGroup> findList();

    public void addCheckGroup(CheckGroup checkGroup, Integer[] checkitemIds);

    void delCheckGroup(Integer id);

    CheckGroup findCheckGroup(Integer id);

    List<Integer> findCheckItemId(Integer id);

    void editCheckGroup(CheckGroup checkGroup, Integer[] checkitemIds);
}
