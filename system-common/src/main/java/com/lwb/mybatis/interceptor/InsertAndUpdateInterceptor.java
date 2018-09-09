package com.lwb.mybatis.interceptor;

import com.lwb.entity.BaseEntity;
import com.lwb.mybatis.SqlProviderUtil;
import com.lwb.mybatis.sequence.SnowFlakeSequence;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author liuweibo
 * @date 2018/9/3
 */
@Intercepts(
    {@Signature(
        type = Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class}
    )}
)
public class InsertAndUpdateInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement statement = (MappedStatement) args[0];
        Object params = args[1];

        SqlCommandType sqlCommandType = statement.getSqlCommandType();
        switch (sqlCommandType) {
            // 插入操作
            case INSERT: {
                // 获取插入数据的类型
                Class<?> entityCls = this.getEntityCls(params);
                // 获取主键字段
                Field idField = this.getIdField(entityCls);

                if (params instanceof Map) {
                    if (params instanceof Map) {
                        Map<String, Object> maps = (Map<String, Object>) params;
                        Iterator<String> it = maps.keySet().iterator();
                        while (it.hasNext()) {
                            String next = it.next();
                            Object o = maps.get(next);
                            this.setInsertTime(o);
                            this.setId(o, idField);
                        }
                    } else {
                        this.setInsertTime(params);
                    }
                }
                break;
            }
            // 更新操作
            case UPDATE: {
                if (params instanceof Map) {
                    Map<String, Object> maps = (Map<String, Object>) params;
                    Iterator<String> it = maps.keySet().iterator();
                    while (it.hasNext()) {
                        String next = it.next();
                        Object o = maps.get(next);
                        this.setUpdateTime(o);
                    }
                } else {
                    this.setUpdateTime(params);
                }
                break;
            }
        }
        return invocation.proceed();
    }

    /**
     * 给插入实体的id字段赋值
     *
     * @param o
     * @param idField
     */
    private void setId(Object o, Field idField) {
        SqlProviderUtil.setFieldValue(idField, SnowFlakeSequence.next(), o);
    }

    /**
     * 获取插入数据实体的主键字段
     *
     * @param cls
     * @return
     */
    private Field getIdField(Class<?> cls) {
        return SqlProviderUtil.getIdField(cls);
    }

    /**
     * 获取插入数据的实体类型
     *
     * @param params
     * @return
     */
    private Class<?> getEntityCls(Object params) {
        Class<?> result = null;
        if (params instanceof Map) {
            Map<String, Object> maps = (Map<String, Object>) params;
            Iterator<String> it = maps.keySet().iterator();
            while (it.hasNext()) {
                String next = it.next();
                Object entity = maps.get(next);
                if (entity instanceof List) {
                    List list = (List) entity;
                    if (CollectionUtils.isNotEmpty(list)) {
                        result = list.get(0).getClass();
                    } else {
                        result = null;
                    }
                } else {
                    result = entity.getClass();
                }
            }
        } else {
            result = params.getClass();
        }
        return result;
    }

    /**
     * 设置数据更新时间
     *
     * @param o
     */
    private void setUpdateTime(Object o) {
        Date updateTime = new Date();
        if (o instanceof BaseEntity) {
            BaseEntity entity = (BaseEntity) o;
            if (Objects.isNull(entity.getUpdateTime())) {
                entity.setUpdateTime(updateTime);
            }
        }
    }

    /**
     * 设置数据插入时间
     *
     * @param o
     */
    private void setInsertTime(Object o) {
        Date insertTime = new Date();
        if (o instanceof BaseEntity) {
            BaseEntity entity = (BaseEntity) o;
            if (Objects.isNull(entity.getCreateTime())) {
                entity.setCreateTime(insertTime);
            }
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
