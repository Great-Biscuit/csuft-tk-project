package com.csuft.service.impl;

import com.csuft.common.PageResult;
import com.csuft.common.QueryPageBean;
import com.csuft.entity.Member;
import com.csuft.mapper.MemberMapper;
import com.csuft.service.MemberService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberImpl implements MemberService {

    @Autowired
    public MemberMapper memberMapper;

    @Override
    public List<Member> findList() {
        return memberMapper.findList();
    }

    @Override
    public void addMember(Member member) {
        memberMapper.addMember(member);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<Member> page = memberMapper.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public Member findById(Integer id){
        return memberMapper.findById(id);
    }

    @Override
    public void editMember(Member member) {
        memberMapper.editMember(member);
    }

    @Override
    public void delMember(Integer id) {
        memberMapper.delMember(id);
    }
}
