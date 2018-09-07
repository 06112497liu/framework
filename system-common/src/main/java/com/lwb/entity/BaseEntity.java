package com.lwb.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * 实体基础类
 * <note/>所有实体都继承此类
 *
 * @author liuweibo
 * @date 2018/9/6
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseEntity {
    @Id
    Long id;

    @Column(name = "creator_id")
    Long creatorId;

    @Column(name = "updater_id")
    Long updaterId;

    @Column(name = "create_time")
    Date createTime;

    @Column(name = "update_time")
    Date updateTime;
}
