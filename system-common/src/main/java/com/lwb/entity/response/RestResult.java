package com.lwb.entity.response;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author liuweibo
 * @date 2018/6/14
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class RestResult {

    private static final String SUCCESS = "success";

    private static final String FAIL = "fail";

    public String msg;

    private Object data;

    public long responseTime = System.currentTimeMillis();

    public RestResult() {
        this.msg = SUCCESS;
    }

    public RestResult(Object data) {
        this.msg = SUCCESS;
        this.data = data;
    }

    public RestResult(String msg) {
        this.msg = msg;
    }

    public RestResult(Object data, String msg) {
        this.msg = msg;
        this.data = data;
    }

    public static RestResult ok() {
        return new RestResult();
    }

    public static RestResult ok(Object data) {
        return new RestResult(data);
    }

    public static RestResult ok(Object data, String msg) {
        return new RestResult(data, msg);
    }

    public static RestResult fail() {
        return new RestResult(FAIL);
    }
    public static RestResult fail(String message) {
        return new RestResult(message);
    }


    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
