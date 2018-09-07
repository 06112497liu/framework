package com.lwb.entity.exeception;

/**
 * @author liuweibo
 * @date 2018/9/6
 */
public class UtilException extends BaseException {

    public UtilException(String message) {
        super(message);
    }

    public UtilException(String message, Throwable cause) {
        super(message, cause);
    }

    public UtilException(Throwable cause) {
        super(cause);
    }
}
