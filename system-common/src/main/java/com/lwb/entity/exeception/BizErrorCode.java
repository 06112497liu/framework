package com.lwb.entity.exeception;

/**
 * @author liuweibo
 * @date 2018/6/14
 */
public enum BizErrorCode implements ErrorCode{

    USRENAME_NOT_BLANK(1000, "用户名不能为空!"),

    AME_NOT_BLANK(1001, "姓名不能为空!"),

    MOBILE_ERROR(1002, "手机号码有误!"),

    PASSWORD_ERROR(1003, "密码必须由6-20位字母和数字组成!"),

    USERNAME_ERROR(1017, "用户名必须由6-20位字母和数字组成!"),

    ID_NOT_NULL(1004, "id不能为空!"),

    USER_NAME_EXIST(1005, "用户名已经存在!"),

    PASSWORD_NOT_BLANK(1006, "密码不能为空!"),

    ACCOUNT_NOT_BLANK(1007, "账号不能为空!"),

    ACCOUNT_OR_PASSWORD_ERROR(1008, "账号或密码错误!"),;

    private int status;

    private String message;

    BizErrorCode(int status, String message) {
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
