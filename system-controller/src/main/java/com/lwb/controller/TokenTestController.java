package com.lwb.controller;

import com.lwb.annotation.Token;
import com.lwb.entity.response.RestResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuweibo
 * @date 2018/6/22
 */
@RestController
@RequestMapping(value = "form/submit")
public class TokenTestController {

    @Token(save = true)
    @RequestMapping("/token/save")
    public RestResult saveToken() {
        return RestResult.ok();
    }

    @Token(remove = true)
    @RequestMapping("/token/remove")
    public RestResult removeToken() {
        return RestResult.ok();
    }
}
