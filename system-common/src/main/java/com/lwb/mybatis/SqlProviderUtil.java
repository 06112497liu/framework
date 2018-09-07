package com.lwb.mybatis;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.lwb.entity.exeception.UtilException;
import com.lwb.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author liuweibo
 * @date 2018/8/23
 */
public interface SqlProviderUtil {

    /**
     * 获取实体对应的表名
     * <pre>
     *     实体所加注解{@link Table#name()}的值即为表名,
     *     如果没加该注解, 默认使用实体的名称作为表名.
     * </pre>
     *
     * @param clazz
     * @param <T>
     * @return
     */
    static String getTableName(Class<?> clazz) {
        return
            Optional.ofNullable(clazz.getAnnotation(Table.class))
                .filter(Objects::nonNull)
                .map(Table::name)
                .filter(StringUtil::isNotEmpty)
                .orElse(humpConvertDatabase(clazz.getSimpleName()));
    }

    /**
     * 获取主键的值
     * <pre>
     *     被{@link Id}标记的字段, 或者字段名为id的字段
     * </pre>
     *
     * @param clazz
     * @return
     */
    static Field getIdField(Class<?> clazz) {
        return
            Optional.of(getFields(clazz))
                .filter(CollectionUtils::isNotEmpty)
                .map(fields -> fields.stream()
                    .filter(field -> Objects.nonNull(field.getAnnotation(Id.class))
                        || "id".equals(field.getName()))
                    .findFirst()
                    .orElse(null)
                )
                .orElseThrow(() -> new UtilException(
                    String.format("No Such Field: %s", "id"))
                );
    }

    /**
     * 获取该类的所有字段(包括父类的字段)
     * <pre>
     *      被@Transient标注的字段、Object中的字段、static修饰的字段除外
     * </pre>
     *
     * @param clazz
     * @return
     */
    static List<Field> getFields(Class<?> clazz) {

        List<Field> fields = Lists.newLinkedList();

        Class<?> tempClass = clazz;
        while (Objects.nonNull(tempClass) && !Objects.equals(Object.class, tempClass)) {

            List<Field> list = Stream.of(tempClass.getDeclaredFields())
                .filter(field -> !Modifier.isStatic(field.getModifiers())
                    && Objects.isNull(field.getAnnotation(Transient.class)))
                .collect(Collectors.toList());

            fields.addAll(list);
            tempClass = tempClass.getSuperclass();
        }
        return fields;
    }

    /**
     * 给指定字段赋值
     *
     * @param field
     * @param value
     * @param entity
     */
    static void setFieldValue(Field field, Object value, Object entity) {
        boolean accessible = field.isAccessible();
        if (!accessible) {
            field.setAccessible(true);
        }
        try {
            field.set(entity, value);
            if (accessible) {
                field.setAccessible(false);
            }
        } catch (IllegalAccessException e) {
            throw new UtilException(e.getMessage());
        }
    }

    /**
     * 获取字段的值
     *
     * @param field
     * @param entity
     * @return
     */
    static Object getFieldValue(Field field, Object entity) {
        boolean accessible = field.isAccessible();
        if (!accessible) {
            field.setAccessible(true);
        }
        Object value;
        try {
            value = field.get(entity);
        } catch (IllegalAccessException e) {
            throw new UtilException(e.getMessage());
        }
        if (accessible) {
            field.setAccessible(false);
        }
        return value;
    }

    /**
     * 获取数据库字段名
     * <pre>
     *     先试图拿字段上{@link Column#name()}的值,
     *     如果没有, 将驼峰命名法的字段名称转化为数据库字段的命名规则
     * </pre>
     *
     * @param field
     * @return
     */
    static String getColumnName(Field field) {
        Preconditions.checkNotNull(field);
        return
            Optional.ofNullable(field.getAnnotation(Column.class))
                .filter(annotation -> Objects.nonNull(annotation)
                    && StringUtils.isNotEmpty(annotation.name()))
                .map(Column::name)
                .orElse(humpConvertDatabase(field.getName()));

    }

    /**
     * 驼峰命名法 -> 数据库命名法
     *
     * @param humpName
     * @return
     */
    static String humpConvertDatabase(String humpName) {
        Preconditions.checkNotNull(humpName);
        if (humpName.equals(humpName.toUpperCase())) {
            return humpName;
        }
        StringBuffer sb = new StringBuffer();
        Optional.ofNullable(humpName)
            .filter(StringUtils::isNotEmpty)
            .ifPresent(name -> {
                char[] chars = name.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    char temp = chars[i];
                    if (CharUtils.isAsciiAlphaUpper(temp)) {
                        temp = (char) (temp + 32);
                        if (i != 0) {
                            sb.append("_");
                        }
                    }
                    sb.append(temp);
                }
            });
        return sb.toString();
    }

}
























