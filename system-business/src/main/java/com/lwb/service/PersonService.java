package com.lwb.service;

import com.github.pagehelper.PageInfo;
import com.lwb.entity.response.PageList;
import com.lwb.vo.PersonVo;

import java.util.List;

/**
 * @author liuweibo
 * @date 2018/6/15
 */
public interface PersonService {

    /**
     * 根据id查询person
     * @param id person的id
     * @return {@link PersonVo}
     */
    PersonVo getPersonByID(Integer id);

    /**
     * 分页查询学生信息
     * @param pageNum 当前页
     * @param pageSize 每页数量
     * @return {@link List<PersonVo>}
     */
    PageList<PersonVo> getPage(Integer pageNum, Integer pageSize);
}
