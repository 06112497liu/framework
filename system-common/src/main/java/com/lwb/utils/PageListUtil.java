package com.lwb.utils;

import com.github.pagehelper.Page;
import com.lwb.entity.response.PageList;

import java.util.List;

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
     * @param <T>   转化对象
     * @return
     */
    static <T> PageList<T> create(Page<?> page, Class<T> clazz) {
        return
            PageList.<T>builder()
                .data(BeanMapperUtil.mapList(page, clazz))
                .pageNum(page.getPageNum())
                .pageSize(page.getPageSize())
                .pages(page.getPages())
                .total(page.getTotal())
                .build();
    }

    /**
     * 构建返回页面的分页对象
     *
     * @param data     原始数据
     * @param pageNum  当前页
     * @param pageSize 当前页大小
     * @param total    总记录数
     * @param clazz    转化对象VO
     * @param <T>
     * @return
     */
    static <T> PageList<T> create(List<?> data,
                                  Class<T> clazz,
                                  Integer pageNum,
                                  Integer pageSize,
                                  Integer total) {
        return
            PageList.<T>builder()
                .data(BeanMapperUtil.mapList(data, clazz))
                .pageNum(pageNum)
                .pageSize(pageSize)
                .pages((total / pageSize + ((total % pageSize == 0) ? 0 : 1)))
                .total(total)
                .build();
    }
}
