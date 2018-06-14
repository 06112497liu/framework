package com.lwb.entity.response;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author liuweibo
 * @date 2018/6/14
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class RestResult<T> {

    public static final int CODE_SUCCESS = 200;

    public String msg = "";

    private int status;

    private T data;

    public long responseTime = System.currentTimeMillis();

    public RestResult() {
        this.status = 200;
        this.msg = "ok";
    }

    public RestResult(T data) {
        this.status = 200;
        this.msg = "ok";
        this.data = data;
    }

    public RestResult(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public RestResult(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static RestResult ok() {
        return new RestResult();
    }

    public static RestResult ok(Object data) {
        return new RestResult(data);
    }

    public static RestResult fail(int status, String message) {
        return new RestResult(status, message);
    }

    public static RestResult fail(int status, String message, Object data) {
        return new RestResult(status, message, data);
    }

    public static int getCodeSuccess() {
        return CODE_SUCCESS;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
