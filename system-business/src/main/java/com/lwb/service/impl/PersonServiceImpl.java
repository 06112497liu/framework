package com.lwb.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lwb.dao.PersonDao;
import com.lwb.entity.response.PageList;
import com.lwb.po.Person;
import com.lwb.po.PersonExample;
import com.lwb.service.PersonService;
import com.lwb.utils.BeanMapperUtil;
import com.lwb.utils.PageListUtil;
import com.lwb.vo.PersonVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Stream.builder;

/**
 * @author liuweibo
 * @date 2018/6/15
 */
@Service
public class PersonServiceImpl implements PersonService {

    private final Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Autowired
    private PersonDao personDao;

    /**
     * 根据id查询person
     *
     * @param id person的id
     * @return {@link Person}
     */
    @Override
    public PersonVo getPersonByID(Integer id) {
        return
            BeanMapperUtil.map(
                this.personDao.selectByPrimaryKey(id),
                PersonVo.class
            );
    }

    /**
     * 分页查询学生信息
     *
     * @param pageNum  当前页
     * @param pageSize 每页数量
     * @return {@link List<PersonVo>}
     */
    @Override
    public PageList<PersonVo> getPage(Integer pageNum, Integer pageSize) {
        return
            PageListUtil.create(
                PageHelper.startPage(pageNum, pageSize).doSelectPage(
                    () -> this.personDao.selectByExample(new PersonExample())
                ),
                PersonVo.class
            );
    }
}
