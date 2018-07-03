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
     * @return
     */
    @GetMapping("/search/{name}")
    public RestResult fullTextSearchByName(@PathVariable("name") String name) {
        return RestResult.ok(this.customerService.fullTextSearchByName(name));
    }

}
