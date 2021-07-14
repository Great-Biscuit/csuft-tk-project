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
import java.util.List;
import java.util.Map;

@Service
public class CheckGroupImpl implements CheckGroupService {

    @Autowired
    CheckGroupMapper checkGroupMapper;

    @Override
    public List<CheckGroup> findList() {
        return checkGroupMapper.findList();
    }

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
            checkGroupMapper.addCheckGroupIdAndCheckItemId(map);
        }
    }

    @Override
    public void delCheckGroup(Integer id) {
        //1.查询当前检查组中的id在中间表中是否存在数据
        Long count =  checkGroupMapper.findCheckGroupCount(id);
        //2.如果查询出来的数值大于0说明，对应的检查组数据是有关联的，就要求用户不能删此条数据给用户提示消息
        if(count>0){
            throw new RuntimeException("当前数据有关联，请先清除关联数据!");
        }

        //3.如果查询出来的数值==0，说明中间表中是没有关联数据的，会直接删除检查组的数据
        checkGroupMapper.delCheckGroup(id);
    }

    @Override
    public CheckGroup findCheckGroup(Integer id) {
        return checkGroupMapper.findCheckGroup(id);
    }

    @Override
    public List<Integer> findCheckItemId(Integer id) {
        return checkGroupMapper.findCheckItemId(id);
    }

    @Override
    public void editCheckGroup(CheckGroup checkGroup, Integer[] checkitemIds) {
        //1.将检查组的数据进行更新
        checkGroupMapper.editCheckGroup(checkGroup);
        //2.将以前关联的数据根据检查组的id进行删除
        checkGroupMapper.delCheckGroupAndCheckItem(checkGroup.getId());
        //3.再将现有的关联关系进行保存
        for (Integer id:checkitemIds) {
            Map<String,Integer> map  = new HashMap();
            map.put("checkgroup_id",checkGroup.getId());
            map.put("checkitem_id",id);
            checkGroupMapper.addCheckGroupIdAndCheckItemId(map);
        }
    }
}
