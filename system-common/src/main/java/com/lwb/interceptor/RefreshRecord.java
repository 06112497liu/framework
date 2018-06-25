package com.lwb.interceptor;

import lombok.Builder;
import lombok.Data;

/**
 * @author liuweibo
 * @date 2018/6/25
 */
@Data
public class RefreshRecord {
    private String requestInfo = "";
    private Long startTime = System.currentTimeMillis();
    private int refreshCount = 0;
}
