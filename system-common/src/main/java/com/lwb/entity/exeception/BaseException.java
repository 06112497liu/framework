package com.lwb.entity.exeception;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 异常基础类
 * <pre>
 *     所有异常都继承此类
 * </pre>
 *
 * @author liuweibo
 * @date 2018/9/6
 */
@Data
@NoArgsConstructor
public class BaseException extends RuntimeException {
    /**
     * 状态码
     */
    private Integer code;

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public BaseException(String message, Throwable cause, Integer code) {
        super(message, cause);
        this.code = code;
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }
}
