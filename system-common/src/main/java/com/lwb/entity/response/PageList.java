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
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Builder
@Data
public class PageList<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    //总记录数
    protected long    total;
    //结果集
    protected List<T> data;
    //当前页
    private   int     pageNum;
    //每页的数量
    private   int     pageSize;
    //总页数
    private   int     pages;
}
