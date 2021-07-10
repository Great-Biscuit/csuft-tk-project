package com.csuft.common;

import java.io.Serializable;

/**
 * @Author
 * @Date 2019/12/30 13:42
 * @Version v1.0
 * @Description 封装返回结果
 */
public class Result implements Serializable {

    private Boolean flag; //执行结果,true为执行成功, false为执行失败
    private String message; //返回结果信息
    private Object data; //返回数据

    public Result() {
    }

    public Result(Boolean flag, String message) {
        this.flag = flag;
        this.message = message;
    }

    public Result(Boolean flag, String message, Object data) {
        this.flag = flag;
        this.message = message;
        this.data = data;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "flag=" + flag +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
