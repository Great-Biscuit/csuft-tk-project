package com.csuft.mapper;

import com.csuft.entity.CheckGroup;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface CheckGroupMapper {
    public Page<CheckGroup> findPage(@Param("queryString") String queryString);

    public void addCheckGroup(CheckGroup checkGroup);

    public void addCheckgroupIdAndCheckitemId(Map<String, Integer> map);
}
