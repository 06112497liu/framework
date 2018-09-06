package com.lwb.controller;

import com.lwb.entity.response.RestResult;
import com.lwb.service.BankCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuweibo
 * @date 2018/7/3
 */
@RestController
@RequestMapping("bank")
public class BankCustomerContorller {

    @Autowired
    private BankCustomerService customerService;

    /**
     * 根据银行客户全文检索客户信息
     *
     * @param name 客户姓名
     * @return {@link RestResult}
     */
    @GetMapping("/search/{name}")
    public RestResult fullTextSearchByName(@PathVariable("name") String name) {
        return RestResult.ok(this.customerService.fullTextSearchByName(name));
    }

    /**
     * 查询不能年龄段的客户
     *
     * @return {@link RestResult}
     */
    @GetMapping("/group/age/range")
    public RestResult groupByAgeRange() {
        return RestResult.ok(this.customerService.groupByAgeRange());
    }

    /**
     * 查询不能年龄段的客户
     *
     * @return {@link RestResult}
     */
    @GetMapping("/avg/balance")
    public RestResult aggAvgBalance() {
        return RestResult.ok(this.customerService.aggAvgBalance());
    }

}
