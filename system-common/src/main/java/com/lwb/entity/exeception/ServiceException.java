package com.lwb.entity.exeception;

import lombok.NoArgsConstructor;

/**
 * @author liuweibo
 * @date 2018/9/6
 */
@NoArgsConstructor
public class ServiceException extends BaseException {
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Integer code) {
        super(message, code);
    }

    public ServiceException(String message, Throwable cause, Integer code) {
        super(message, cause, code);
    }
}
