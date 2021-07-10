package com.csuft.service.impl;

import com.csuft.common.PageResult;
import com.csuft.common.QueryPageBean;
import com.csuft.entity.CheckGroup;
import com.csuft.entity.CheckItem;
import com.csuft.mapper.CheckGroupMapper;
import com.csuft.service.CheckGroupService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CheckGroupImpl implements CheckGroupService {

    @Autowired
    CheckGroupMapper checkGroupMapper;

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<CheckGroup> page = checkGroupMapper.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void addCheckGroup(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupMapper.addCheckGroup(checkGroup);
        for (Integer id:checkitemIds) {
            Map<String, Integer> map = new HashMap();
            map.put("checkgroup_id", checkGroup.getId());
            map.put("checkitem_id", id);
            checkGroupMapper.addCheckgroupIdAndCheckitemId(map);
        }
    }
}
