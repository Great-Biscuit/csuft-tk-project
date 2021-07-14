package com.csuft.util;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * 响应JSON格式
 */
public class ResultJSON {

    public static String getJSONString(int code, boolean flag, Map<String, Object> message) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("flag", flag);
        if (message != null) {
            json.put("message", message);
        }
        return json.toString();
    }

    public static String getJSONString(int code, boolean flag) {
        return getJSONString(code, flag, null);
    }

    public static String getJSONString(int code) {
        return getJSONString(code, true, null);
    }

}
