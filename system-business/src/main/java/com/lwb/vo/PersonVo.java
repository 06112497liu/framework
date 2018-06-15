package com.lwb.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author liuweibo
 * @date 2018/6/15
 */
@Data
public class PersonVo {

    private Integer id;

    private String name;

    private Double weight;

    private Double height;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date gmtCreate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date gmtModify;
}


