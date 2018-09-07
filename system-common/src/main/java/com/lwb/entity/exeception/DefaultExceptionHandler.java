package com.lwb.entity.exeception;

import com.lwb.entity.response.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author liuweibo
 * @date 2018/9/6
 */
@ControllerAdvice
@Slf4j
public class DefaultExceptionHandler {

    /**
     * 所有的业务异常 {@link ServiceException}
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = {ServiceException.class})
    public RestResult serviceException(ServiceException e) {
        return RestResult.fail(e.getMessage());
    }

    /**
     * 其他所有异常返回到前端都显示系统异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = {Exception.class})
    public RestResult exception(Exception e) {
        return RestResult.fail("系统异常，请联系管理员！");
    }
}
