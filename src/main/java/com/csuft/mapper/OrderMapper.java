package com.csuft.mapper;

import com.csuft.entity.Order;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface OrderMapper {

    void editOrder(Order order);

    void delOrder(Integer id);

    Page<Map<String, Object>> findPage(@Param("queryString") String queryString);
}
