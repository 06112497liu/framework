package com.lwb.interceptor;

import lombok.Data;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liuweibo
 * @date 2018/6/25
 */
@Data
public class RefreshRecordManager extends ConcurrentHashMap<String, RefreshRecord> {

    private static final long serialVersionUID = 1L;

    private long totalTime = 10 * 1000;

    private int totalCount = 4;

    private java.lang.String redirectPage = "sd";

}
