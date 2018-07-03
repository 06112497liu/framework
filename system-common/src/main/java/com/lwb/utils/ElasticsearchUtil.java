package com.lwb.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;

import java.util.List;

/**
 * @author liuweibo
 * @date 2018/7/3
 */
public class ElasticsearchUtil {

    public static <T> List<T> converterVo(SearchResponse resp, Class<T> clazz) {
        SearchHit[] hits = resp.getHits().getHits();
        List<T> result = Lists.newLinkedList();
        for (SearchHit hit : hits) {
            String str = hit.getSourceAsString();
            T t = JSONObject.parseObject(str, clazz);
            result.add(t);
        }
        return result;
    }


}
