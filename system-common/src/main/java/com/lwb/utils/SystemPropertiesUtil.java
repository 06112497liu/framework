package com.lwb.utils;

import com.lwb.entity.exeception.UtilException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 加载extend.properties文件，用于获取属性
 *
 * @author liuweibo
 * @date 2018/9/7
 */
@Slf4j
public class SystemPropertiesUtil {

    private static final String FILE_NAME = "extend.properties";
    private static Properties properties;

    static {
        properties = new Properties();
        InputStream resourceAsStream = SystemPropertiesUtil.class
            .getClassLoader()
            .getResourceAsStream(FILE_NAME);
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new UtilException(String.format("%s获取失败"));
        }
    }

    public static Object getValue(String key) {
        return properties.get(key);
    }

    public static Object getValue(String key, Object defaultVal) {
        return properties.getOrDefault(key, defaultVal);
    }
}
