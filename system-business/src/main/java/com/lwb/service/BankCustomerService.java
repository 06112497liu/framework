package com.lwb.service;

import com.lwb.vo.BankCustomerVo;
import com.lwb.vo.KeyValueVo;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
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
     * @return {@link BankCustomerVo}
     */
    List<BankCustomerVo> fullTextSearchByName(String name);

    /**
     * 查询不能年龄段的客户
     *
     * @return {@link KeyValueVo}
     */
    List<KeyValueVo> groupByAgeRange();

    /**
     * 范围聚合求账户平均存款
     *
     * @return
     */
    List aggAvgBalance();

}
