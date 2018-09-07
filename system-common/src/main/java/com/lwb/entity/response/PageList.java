package com.lwb.entity.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageSerializable;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author liuweibo
 * @date 2018/6/15
 */
@Builder
@Data
public class PageList<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    //总记录数
    private long    total;
    //结果集
    private List<T> data;
    //当前页
    private int     pageNum;
    //每页的数量
    private int     pageSize;
    //总页数
    private int     pages;
}
