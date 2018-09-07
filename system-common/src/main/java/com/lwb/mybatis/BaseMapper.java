package com.lwb.mybatis.mapper;

import com.lwb.entity.BaseEntity;
import com.lwb.mybatis.BaseSqlProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.io.Serializable;
import java.util.List;

/**
 * @author liuweibo
 * @date 2018/8/23
 */
public interface BaseMapper<T> {

    /**
     * 插入一条数据
     *
     * @param entity
     * @return
     */
    @InsertProvider(type = BaseSqlProvider.class, method = "insert")
    Integer insert(@Param("entity") T entity);

    /**
     * 批量插入数据
     * <pre>
     *     note: 数据列表不能为空，并且不能超过500
     * </pre>
     *
     * @param entities
     * @return
     */
    @InsertProvider(type = BaseSqlProvider.class, method = "batchInsert")
    Integer batchInsert(@Param("entities") List<T> entities);

    /**
     * 根据id更新一条数据
     * <pre>
     *     只更新有值的字段
     * </pre>
     *
     * @param entity
     * @return
     */
    @UpdateProvider(type = BaseSqlProvider.class, method = "update")
    Integer update(@Param("entity") T entity);

    /**
     * 批量根据id更新
     * <pre>
     *     只更新有值的字段
     * </pre>
     *
     * @param entities
     * @return
     */
    @UpdateProvider(type = BaseSqlProvider.class, method = "batchUpdate")
    Integer batchUpdate(@Param("entities") List<T> entities);

    /**
     * 多条件查询
     * <pre>
     *     只更新有值的字段
     * </pre>
     *
     * @param entity
     * @return
     */
    @SelectProvider(type = BaseSqlProvider.class, method = "query")
    List<T> query(@Param("entity") T entity);

    /**
     * 根据id查询一条记录
     *
     * @param id
     * @param clazz 实体类型
     * @return
     */
    @SelectProvider(type = BaseSqlProvider.class, method = "queryById")
    T queryById(@Param("id") Serializable id, @Param("clazz") Class<T> clazz);
}
