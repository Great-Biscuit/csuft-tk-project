package com.csuft.mapper;

import com.csuft.entity.CheckItem;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CheckItemMapper {
    public CheckItem findById(Integer id);

    public List<CheckItem> findList();

    public void addCheckItem(CheckItem checkItem);

    public Page<CheckItem> findPage(@Param("queryString") String queryString);

    public void editCheckItem(CheckItem checkItem);

    public void delCheckItem(Integer id);
}
