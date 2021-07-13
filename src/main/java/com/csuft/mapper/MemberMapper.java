package com.csuft.mapper;

import com.csuft.entity.Member;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {
    public List<Member> findList();

    public void addMember(Member member);

    public Page<Member> findPage(@Param("queryString") String queryString);

    public Member findById(Integer id);

    public void editMember(Member member);

    public void delMember(Integer id);
}
