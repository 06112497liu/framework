package com.lwb.reflect;

import com.lwb.utils.ReflectUtil;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

/**
 * @author liuweibo
 * @date 2018/8/23
 */
public class Demo {

    public static void main(String[] args) {
        List<Field> fields = ReflectUtil.getFields(new Person().getClass());
        System.out.println(fields);
    }
}

@Data
class BaseEntity {
    private Integer id;
    private Date createTime;
}

@Data
class Person extends BaseEntity {

    private String name;

    private Integer value = 1;

}
