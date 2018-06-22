package com.lwb.entity.exeception;

/**
 * @author liuweibo
 * @date 2018/6/14
 */
public enum CommonErrorCode implements ErrorCode {

    SUCCESS(200, "成功"),
    SYS_ERROR(101, "系统异常"),
    INNER_ERROR(102, "内部异常"),
    PARAM_ERROR(103, "参数异常"),
    BIZ_ERROR(104, "业务异常"),
    REPEAT_SUBMIT(105, "表单重复提交");

    private int status;
    private String message;

    CommonErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
