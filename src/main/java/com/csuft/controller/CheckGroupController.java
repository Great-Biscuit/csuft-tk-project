package com.csuft.controller;

import com.csuft.annotation.LoginRequired;
import com.csuft.common.MessageConst;
import com.csuft.common.PageResult;
import com.csuft.common.QueryPageBean;
import com.csuft.common.Result;
import com.csuft.entity.CheckGroup;
import com.csuft.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checkGroup")
public class CheckGroupController {
    @Autowired CheckGroupService checkGroupService;

    //查询数据列表(不带分页的)
    @LoginRequired
    @RequestMapping("/findList")
    public Result findList(){
        try{
            List<CheckGroup> list = checkGroupService.findList();
            return new Result(true,MessageConst.QUERY_CHECKGROUP_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConst.QUERY_CHECKGROUP_FAIL);
        }
    }

    @LoginRequired
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult result = checkGroupService.findPage(queryPageBean);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new PageResult(0L,null);
        }
    }

    @LoginRequired
    @RequestMapping("/addCheckGroup")
    public Result addCheckGroup(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        try {
            checkGroupService.addCheckGroup(checkGroup, checkitemIds);
            return new Result(true, MessageConst.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.ADD_CHECKGROUP_FAIL);
        }
    }

    //删除检查组的数据
    @LoginRequired
    @RequestMapping("/delCheckGroup")
    public Result delCheckGroup(Integer id){
        try{
            //1.调用service的方法
            checkGroupService.delCheckGroup(id);
            //2.返回删除的结果;
            return new Result(true,MessageConst.DELETE_CHECKGROUP_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            if(e.getMessage().contains("关联数据")){
                return new Result(false,e.getMessage());
            }else{
                return  new Result(false,MessageConst.DELETE_CHECKGROUP_FAIL);
            }
        }
    }
    //根据检查组的id查询检查组对象
    @LoginRequired
    @RequestMapping("/findCheckGroup")
    public Result findCheckGroup(Integer id){
        try{
            //1.调用service中的方法
            CheckGroup c = checkGroupService.findCheckGroup(id);
            //2.将查询到的对象返回
            return new Result(true,MessageConst.QUERY_CHECKGROUP_SUCCESS,c);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConst.QUERY_CHECKGROUP_FAIL);
        }
    }
    //查询检查组关联的检查项id
    @LoginRequired
    @RequestMapping("/findCheckItemId")
    public Result findCheckItemId(Integer id){
        try{
            //1.调用service查询检查项id
            List<Integer> list =  checkGroupService.findCheckItemId(id);
            //2.将查询到的数据返回
            return new Result(true,MessageConst.QUERY_CHECKGROUP_CHECKITEM_ID_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConst.QUERY_CHECKGROUP_CHECKITEM_ID_FAIL);
        }
    }
    //检查组数据编辑功能实现
    @LoginRequired
    @RequestMapping("/editCheckGroup")
    public Result editCheckGroup(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds) {
        try {
            //1.调用service实现修改功能
            checkGroupService.editCheckGroup(checkGroup, checkitemIds);
            //返回修改成功结果
            return new Result(true, MessageConst.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.EDIT_CHECKGROUP_FAIL);
        }
    }
}
