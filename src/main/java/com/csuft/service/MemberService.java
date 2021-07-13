package com.csuft.service;

import com.csuft.common.PageResult;
import com.csuft.common.QueryPageBean;
import com.csuft.entity.Member;

import java.util.List;

public interface MemberService {
    public List<Member> findList();

    public void addMember(Member member);

    public PageResult findPage(QueryPageBean queryPageBean);

    public Member findById(Integer id);

    public void editMember(Member member);

    public void delMember(Integer id);
}
