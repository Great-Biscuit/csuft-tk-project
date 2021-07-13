package com.csuft.controller;

import com.csuft.annotation.LoginRequired;
import com.csuft.common.MessageConst;
import com.csuft.common.PageResult;
import com.csuft.common.QueryPageBean;
import com.csuft.common.Result;
import com.csuft.entity.Member;
import com.csuft.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Member")
public class MemberController {
    @Autowired
    MemberService memberService;

    @LoginRequired
    @RequestMapping("/findList")
    public Result findList() {
        List<Member> list = memberService.findList();
        if (list != null) {
            return new Result(true, MessageConst.QUERY_MEMBER_SUCCESS, list);
        } else {
            return new Result(false, MessageConst.QUERY_MEMBER_FAIL);
        }
    }
    @LoginRequired
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult result = memberService.findPage(queryPageBean);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new PageResult(0L,null);
        }
    }

    @LoginRequired
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            Member member = memberService.findById(id);
            return new Result(true, MessageConst.QUERY_MEMBER_SUCCESS, member);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.QUERY_MEMBER_FAIL);
        }
    }

    @LoginRequired
    @RequestMapping("/addMember")
    public Result addMember(@RequestBody Member member) {
        try {
            memberService.addMember(member);
            return new Result(true, MessageConst.ADD_MEMBER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.ADD_MEMBER_FAIL);
        }
    }

    @LoginRequired
    @RequestMapping("/editMember")
    public Result editMember(@RequestBody Member member) {
        try {
            memberService.editMember(member);
            return new Result(true, MessageConst.EDIT_MEMBER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.EDIT_MEMBER_FAIL);
        }
    }

    @LoginRequired
    @RequestMapping("/delMember")
    public Result delMember(Integer id) {
        try {
            memberService.delMember(id);
            return new Result(true, MessageConst.DELETE_MEMBER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.DELETE_MEMBER_FAIL);
        }
    }
}
