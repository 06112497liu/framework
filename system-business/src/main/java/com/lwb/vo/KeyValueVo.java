package com.lwb.vo;

import lombok.Data;

import java.util.List;

/**
 * @author liuweibo
 * @date 2018/7/3
 */
@Data
public class KeyValueVo {

    private String key;

    private Long docCount;

    private List<KeyValueVo> buckets;
}
