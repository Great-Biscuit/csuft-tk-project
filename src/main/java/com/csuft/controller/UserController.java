package com.csuft.controller;

import com.csuft.annotation.LoginRequired;
import com.csuft.util.HostHolder;
import com.csuft.util.MapTools;
import com.csuft.util.ResultJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @Autowired
    private HostHolder hostHolder;

    @LoginRequired
    @GetMapping("/getUserInfo")
    @ResponseBody
    public String getUserInfo() {
        try {
            return ResultJSON.getJSONString(200, true, MapTools.objectToMap(hostHolder.getUser()));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return ResultJSON.getJSONString(200, false);
    }

}
