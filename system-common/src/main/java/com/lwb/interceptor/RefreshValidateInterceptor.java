package com.lwb.interceptor;

import com.lwb.annotation.RefreshValidate;
import com.lwb.entity.exeception.ExConstant;
import com.lwb.entity.exeception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liuweibo
 * @date 2018/6/25
 */
public class RefreshValidateInterceptor extends HandlerInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(RefreshValidateInterceptor.class);

    private ConcurrentHashMap<String, RefreshRecordManager> map = new ConcurrentHashMap<>();

    public RefreshValidateInterceptor() {
        new Thread(new CleanThread()).start();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RefreshValidate annotation = method.getAnnotation(RefreshValidate.class);
            if (Objects.nonNull(annotation)) {
                if (this.isFrequent(request)) {
                    throw new ServiceException(ExConstant.REPEAT_SUBMIT);
                }
            }
        }
        return super.preHandle(request, response, handler);
    }

    /**
     * 校验是否请求频繁
     *
     * @param request
     * @return
     */
    private boolean isFrequent(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String uri = request.getRequestURI();
        RefreshRecordManager recordManager;
        RefreshRecord refreshRecord;
        if (this.map.containsKey(session.getId())) {
            recordManager = this.map.get(session.getId());
            if (recordManager.contains(uri)) {
                refreshRecord = recordManager.get(uri);
                refreshRecord.setRefreshCount(refreshRecord.getRefreshCount() + 1);
                if (System.currentTimeMillis() - refreshRecord.getStartTime() < recordManager.getTotalTime()) {
                    if (refreshRecord.getRefreshCount() > recordManager.getTotalCount()) {
                        return true;
                    }
                } else {
                    refreshRecord.setRefreshCount(1);
                    refreshRecord.setRequestInfo(uri);
                    refreshRecord.setStartTime(System.currentTimeMillis());
                }
            } else {
                refreshRecord = new RefreshRecord();
                refreshRecord.setStartTime(System.currentTimeMillis());
                refreshRecord.setRefreshCount(1);
                refreshRecord.setRequestInfo(uri);
                recordManager.put(uri, refreshRecord);
            }
        } else {
            refreshRecord = new RefreshRecord();
            refreshRecord.setStartTime(System.currentTimeMillis());
            refreshRecord.setRefreshCount(1);
            refreshRecord.setRequestInfo(uri);
            recordManager = new RefreshRecordManager();
            recordManager.put(uri, refreshRecord);
            this.map.put(session.getId(), recordManager);
        }
        return false;
    }

    /**
     * 清理内存，防止内存泄漏
     */
    class CleanThread implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    clean();
                    Thread.sleep(2000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void clean() {
            if (map.size() > 0) {
                for (Map.Entry<String, RefreshRecordManager> entry : map.entrySet()) {
                    RefreshRecordManager recordManager = entry.getValue();
                    for (Map.Entry<String, RefreshRecord> record : recordManager.entrySet()) {
                        if (System.currentTimeMillis() - record.getValue().getStartTime() > recordManager.getTotalTime()) {
                            recordManager.remove(record.getKey());
                        }
                    }
                }
            }
        }
    }
}

















