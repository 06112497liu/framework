package com.lwb.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author liuweibo
 * @date 2018/6/14
 */
public interface BeanMapperUtil {

    Logger logger = LoggerFactory.getLogger(BeanMapperUtil.class);

    static <T> T map(Object sourceObject, Class<T> destObjectclazz) {
        if (sourceObject == null) {
            return null;
        } else {
            T obj = null;

            try {
                obj = destObjectclazz.newInstance();
            } catch (InstantiationException var6) {
                logger.warn(var6.getMessage(), var6);
            } catch (IllegalAccessException var7) {
                logger.warn(var7.getMessage(), var7);
            }

            try {
                BeanUtils.copyProperties(obj, sourceObject);
            } catch (IllegalAccessException var4) {
                logger.error(var4.getMessage(), var4);
            } catch (InvocationTargetException var5) {
                logger.error(var5.getMessage(), var5);
            }
            return obj;
        }
    }

    static <T, S> List<T> mapList(Collection<S> sourceList, Class<T> destObjectclazz) {
        List<T> destinationList = new ArrayList();
        if (sourceList != null && sourceList.size() != 0) {
            Iterator it = sourceList.iterator();

            while(it.hasNext()) {
                destinationList.add(map(it.next(), destObjectclazz));
            }

            return destinationList;
        } else {
            return destinationList;
        }
    }
}
