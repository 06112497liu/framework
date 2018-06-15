package com.lwb.utils;

import com.github.pagehelper.Page;
import com.lwb.entity.response.PageList;

/**
 * @author liuweibo
 * @date 2018/6/15
 */
public interface PageListUtil {

    /**
     * 构建返回页面的分页对象
     *
     * @param page  原始分页数据
     * @param clazz 要转化成的vo
     * @param <E>   原始对象
     * @param <T>   转化对象
     * @return
     */
    static <E, T> PageList<T> create(Page<E> page, Class<T> clazz) {
        return
            PageList.<T>builder()
                .list(BeanMapperUtil.mapList(page, clazz))
                .pageNum(page.getPageNum())
                .pageSize(page.getPageSize())
                .pages(page.getPages())
                .total(page.getTotal())
                .build();
    }
}
