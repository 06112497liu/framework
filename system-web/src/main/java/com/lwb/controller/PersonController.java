package com.lwb.controller;

import com.github.pagehelper.PageInfo;
import com.lwb.entity.response.RestResult;
import com.lwb.po.Person;
import com.lwb.service.PersonService;
import com.lwb.vo.PersonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author liuweibo
 * @date 2018/6/15
 */
@RestController
@RequestMapping("person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @RequestMapping("/{id}")
    public RestResult personById(@PathVariable("id") Integer id) {
        return
            RestResult.ok(
                this.personService.getPersonByID(id)
            );
    }

    @RequestMapping("/page/{pageNum}/{pageSize}")
    public RestResult page(@PathVariable("pageNum") Integer pageNum,
                                         @PathVariable("pageSize") Integer pageSize) {
        return
            RestResult.ok(
                this.personService.getPage(pageNum, pageSize)
            );
    }

}
