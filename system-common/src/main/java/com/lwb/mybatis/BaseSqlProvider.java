package com.lwb.mybatis;

import com.google.common.base.Preconditions;
import com.lwb.constant.SymbolConstant;
import com.lwb.entity.exeception.UtilException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author liuweibo
 * @date 2018/8/23
 */
@Slf4j
public class BaseSqlProvider {

    private final String paramName = "entity";

    private final String listParamName = "entities";

    /**
     * 插入一条数据
     *
     * @param params
     * @return
     * @
     */
    public String insert(Map<String, Object> params) {
        Object entity = params.get(this.paramName);
        Preconditions.checkNotNull(entity);
        SQL sql = new SQL();
        sql.INSERT_INTO(SqlProviderUtil.getTableName(entity.getClass()));
        Field idField = SqlProviderUtil.getIdField(entity.getClass());

        List<Field> fields = SqlProviderUtil.getFields(entity.getClass());
        for (Field field : fields) {
            if (Objects.nonNull(SqlProviderUtil.getFieldValue(field, entity))) {
                sql.VALUES(SqlProviderUtil.getColumnName(field), String.format("#{entity.%s}", field.getName()));
            }
        }
        return sql.toString();
    }

    /**
     * 批量插入数据
     * <pre>
     *     note: 插入的数据列表不能为空，也不能超过500条
     * </pre>
     *
     * @param params
     * @return
     */
    public String batchInsert(Map<String, List<Object>> params) {

        return
            Optional.ofNullable(params.get(this.listParamName))
                .filter(entities -> CollectionUtils.isNotEmpty(entities) && entities.size() <= 500)
                .map(entities -> {
                    StringBuffer sb = new StringBuffer("INSERT INTO ");


                    Object entity = entities.get(0);
                    List<Field> fields = SqlProviderUtil.getFields(entity.getClass());
                    sb.append(SqlProviderUtil.getTableName(entity.getClass()));

                    sb.append(SymbolConstant.RIGHT_PARENTHESIS);

                    for (Field field : fields) {
                        sb.append(SqlProviderUtil.getColumnName(field)).append(SymbolConstant.COMMA);
                    }

                    sb.deleteCharAt(sb.length() - 1);
                    sb.append(SymbolConstant.LEFT_PARENTHESIS);
                    sb.append(" VALUES ");

                    for (int i = 0; i < entities.size(); i++) {
                        sb.append(SymbolConstant.RIGHT_PARENTHESIS);

                        for (Field field : fields) {
                            sb.append(String.format("#{entities[%d].%s}", i, field.getName()))
                                .append(SymbolConstant.COMMA);
                        }

                        sb.deleteCharAt(sb.length() - 1)
                            .append(SymbolConstant.LEFT_PARENTHESIS)
                            .append(SymbolConstant.COMMA);
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    return sb.toString();
                })
                .orElseThrow(() -> new UtilException("批量插入的数据列表不能为空，也不能大于500"));


    }

    /**
     * 根据id更新一条数据
     * <pre>
     *     只更新实体有值的字段, 并且不更新id字段
     * </pre>
     *
     * @param params
     * @return
     */
    public String update(Map<String, Object> params) {
        return this.update(params, false);
    }

    /**
     * 根据id更新一条数据
     * <pre>
     *     更新所有字段，值为null的字段也会被更新null
     * </pre>
     *
     * @param params
     */
    public String updateAllColumn(Map<String, Object> params) {
        return this.update(params, true);
    }

    /**
     * 根据id更新一条记录
     *
     * @param params
     * @param allColumn 是否是更新所有字段
     * @return
     */
    private String update(Map<String, Object> params, boolean allColumn) {
        Object entity = params.get(this.paramName);
        Preconditions.checkNotNull(entity);

        SQL sql = new SQL();
        sql.UPDATE(SqlProviderUtil.getTableName(entity.getClass()));

        Field idField = SqlProviderUtil.getIdField(entity.getClass());
        Object idFieldValue = SqlProviderUtil.getFieldValue(idField, entity);
        if (Objects.isNull(idFieldValue)) {
            throw new UtilException("id字段的值不能为空");
        }
        List<Field> fields = SqlProviderUtil.getFields(entity.getClass());

        for (Field field : fields) {
            // 不更新id的值
            if (!Objects.equals(idField, field)) {
                // condition1: 更新所有字段
                if (allColumn) {
                    sql.SET(String.format("%s=#{entity.%s}", SqlProviderUtil.getColumnName(field), field.getName()));
                } else {
                    // condition2: 只更新有值的字段
                    if (Objects.nonNull(SqlProviderUtil.getFieldValue(field, entity))) {
                        sql.SET(String.format("%s=#{entity.%s}", SqlProviderUtil.getColumnName(field), field.getName()));
                    }
                }
            }
        }

        sql.WHERE(String.format("%s=#{entity.%s}", idField.getName(), idField.getName()));
        return sql.toString();
    }

    /**
     * 根据id批量更新
     * <pre>
     *     只更新有值的字段
     * </pre>
     *
     * @param params
     * @return
     */
    public String batchUpdate(Map<String, List<Object>> params) {
        return
            Optional.ofNullable(params.get(this.listParamName))
                .filter(entities -> CollectionUtils.isNotEmpty(entities) && entities.size() <= 500)
                .map(entities -> {
                    Object entity = entities.get(0);

                    Field idField = SqlProviderUtil.getIdField(entity.getClass());
                    String idName = SqlProviderUtil.getColumnName(idField);
                    List<Field> fields = SqlProviderUtil.getFields(entity.getClass());
                    String tableName = SqlProviderUtil.getTableName(entity.getClass());

                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < entities.size(); i++) {
                        sb.append("UPDATE")
                            .append(SymbolConstant.SPACE)
                            .append(tableName)
                            .append(SymbolConstant.SPACE)
                            .append("SET")
                            .append(SymbolConstant.SPACE);
                        for (Field f : fields) {
                            String columnName = SqlProviderUtil.getColumnName(f);
                            if (!Objects.equals(idField, f) &&
                                Objects.nonNull(SqlProviderUtil.getFieldValue(f, entities.get(0)))) {

                                sb.append(String.format("%s = #{entities[%d].%s}", columnName, i, f.getName()))
                                    .append(SymbolConstant.COMMA)
                                    .append(SymbolConstant.SPACE);
                            }
                        }
                        sb.deleteCharAt(sb.length() - 2)
                            .append(SymbolConstant.SPACE)
                            .append(String.format("WHERE %s = #{entities[%d].%s}", idName, i, idField.getName()))
                            .append(SymbolConstant.SEMICOLON)
                            .append(SymbolConstant.SPACE);

                    }
                    return sb.toString();
                })
                .orElseThrow(() -> new UtilException("批量插入的数据列表不能为空，也不能大于500"));
    }

    /**
     * 多条件查询
     *
     * @param params
     * @return
     */
    public String query(Map<String, Object> params) {
        return
            Optional.ofNullable(params.get(this.paramName))
                .filter(entity -> Objects.nonNull(entity))
                .map(entity -> {
                    SQL sql = new SQL();
                    String tableName = SqlProviderUtil.getTableName(entity.getClass());
                    sql.FROM(tableName);
                    List<Field> fields = SqlProviderUtil.getFields(entity.getClass());
                    for (Field f : fields) {
                        String columnName = SqlProviderUtil.getColumnName(f);
                        sql.SELECT(String.format("%s AS %s", columnName, f.getName()));
                        if (Objects.nonNull(SqlProviderUtil.getFieldValue(f, entity))) {
                            sql.WHERE(String.format("%s = #{entity.%s}", columnName, f.getName()));
                        }
                    }
                    return sql.toString();
                })
                .orElseThrow(() -> new UtilException("查询参数不能为空"));
    }

    /**
     * 根据id查询一条记录
     *
     * @param params
     * @return
     */
    public String queryById(Map<String, Object> params) {
        return
            Optional.ofNullable((Class<?>) params.get("clazz"))
                .filter(Objects::nonNull)
                .map(cls -> Optional.of(SqlProviderUtil.getFields(cls))
                    .filter(CollectionUtils::isNotEmpty)
                    .map(fields -> {
                        String tableName = SqlProviderUtil.getTableName(cls);
                        Field idField = SqlProviderUtil.getIdField(cls);
                        String idName = SqlProviderUtil.getColumnName(idField);

                        SQL sql = new SQL();
                        sql.FROM(tableName);
                        sql.WHERE(String.format("%s = #{%s}", idName, idField.getName()));

                        fields.forEach(field -> sql.SELECT(SqlProviderUtil.getColumnName(field)));
                        return sql.toString();
                    })
                    .orElseThrow(() -> new UtilException(String.format("%s没有任何字段", cls))))
                .orElseThrow(NullPointerException::new);

    }
}