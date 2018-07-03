package com.lwb.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.bucket.range.InternalRange;

import java.util.List;

/**
 * @author liuweibo
 * @date 2018/7/3
 */
public class ElasticsearchUtil {

    public static <T> List<T> converterDocToVo(SearchResponse resp, Class<T> clazz) {
        SearchHit[] hits = resp.getHits().getHits();
        List<T> result = Lists.newLinkedList();
        for (SearchHit hit : hits) {
            String str = hit.getSourceAsString();
            T t = JSONObject.parseObject(str, clazz);
            result.add(t);
        }
        return result;
    }

    public static <T> List<T> converterAggToVo(SearchResponse resp, Class<T> clazz, String aggName) {
        Aggregation aggregation = resp.getAggregations().get(aggName);
        List<T> result = Lists.newLinkedList();
        if (aggregation instanceof InternalRange) {
            InternalRange internalRange = (InternalRange) aggregation;
            List<InternalRange.Bucket> buckets = internalRange.getBuckets();
            buckets.forEach(o -> {
                T t = JSONObject.parseObject(JSONObject.toJSONString(o), clazz);
                result.add(t);
            });
        }
        return result;
    }

}
