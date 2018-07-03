package com.lwb.service;

import com.lwb.vo.BankCustomerVo;

import java.util.List;

/**
 * @author liuweibo
 * @date 2018/7/3
 */
public interface BankCustomerService {

    /**
     * 根据客户名称全文检索客户信息
     *
     * @param name 客户名称
     * @return
     */
    List<BankCustomerVo> fullTextSearchByName(String name);
}
