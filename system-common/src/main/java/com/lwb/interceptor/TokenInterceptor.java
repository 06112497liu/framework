package com.lwb.interceptor;

import com.lwb.annotation.Token;
import com.lwb.entity.exeception.ExConstant;
import com.lwb.entity.exeception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author liuweibo
 * @date 2018/6/22
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);

    private final Lock lock = new ReentrantLock();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Token token = method.getAnnotation(Token.class);
            if (token != null) {
                if (token.save()) {
                    String uuid = UUID.randomUUID().toString().replace("-", "");
                    request.getSession().setAttribute("token", uuid);
                    logger.info("进入表单页面, token值为: {}", uuid);
                }
                if (token.remove()) {
                    lock.lock();
                    try {
                        if (isRepeatSubmit(request)) {
                            logger.error(ExConstant.REPEAT_SUBMIT);
                            throw new ServiceException(ExConstant.REPEAT_SUBMIT);
                        }
                        request.getSession(false).removeAttribute("token");
                    } finally {
                        lock.unlock();
                    }
                }
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }

    /**
     * 校验表单是否重复提交
     *
     * @param request
     * @return
     */
    private boolean isRepeatSubmit(HttpServletRequest request) {
        String serverToken = (String) request.getSession(false).getAttribute("token");
        if (serverToken == null) {
            return true;
        }
        String clientToken = request.getParameter("token");
        if (StringUtils.isEmpty(clientToken)) {
            return true;
        }
        if (!Objects.equals(serverToken, clientToken)) {
            return true;
        }
        logger.info("校验表单是否重复提交: 表单页面的token值为: {}, session中的token值为: {}", clientToken, serverToken);
        return false;
    }

}
