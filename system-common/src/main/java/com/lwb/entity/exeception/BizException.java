package com.lwb.entity.exeception;

/**
 * @author liuweibo
 * @date 2018/6/14
 */
public class BizException extends RuntimeException{

    private ErrorCode errorCode;

    public BizException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BizException(ErrorCode errorCode, Throwable ex) {
        super(errorCode.getMessage(), ex);
        this.errorCode = errorCode;
    }

    public BizException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BizException(ErrorCode errorCode, String message, Throwable ex) {
        super(message, ex);
        this.errorCode = errorCode;
    }

    public String toString() {
        return this.errorCode.getMessage();
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }

}
