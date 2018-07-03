package com.lwb.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author liuweibo
 * @date 2018/7/3
 */
@Data
public class BankCustomerVo {

    private Integer accountNumber;

    private Double balance;

    private String firstname;

    private String lastname;

    private Integer age;

    private String gender;

    private String address;

    private String employer;

    private String email;

    private String city;

    private String state;
}
