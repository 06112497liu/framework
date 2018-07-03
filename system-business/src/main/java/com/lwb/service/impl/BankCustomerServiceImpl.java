package com.lwb.service.impl;

import com.google.common.collect.Maps;
import com.lwb.entity.elasticsearch.EsConstant;
import com.lwb.service.BankCustomerService;
import com.lwb.utils.ElasticsearchUtil;
import com.lwb.vo.BankCustomerVo;
import com.lwb.vo.KeyValueVo;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.InternalAggregation;
import org.elasticsearch.search.aggregations.bucket.range.InternalRange;
import org.elasticsearch.search.aggregations.bucket.range.RangeAggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author liuweibo
 * @date 2018/7/3
 */
@Service
public class BankCustomerServiceImpl implements BankCustomerService {

    @Autowired
    private TransportClient client;

    /**
     * 根据客户名称全文检索客户信息
     *
     * @param name 客户名称
     * @return {@link BankCustomerVo}
     */
    @Override
    public List<BankCustomerVo> fullTextSearchByName(String name) {

        // 设置要查询的字段，以及字段的重要性
        Map<String, Float> fields = Maps.newHashMap();
        fields.put(EsConstant.bank_index_field_firstname, (float) 2.0);
        fields.put(EsConstant.bank_index_field_lastname, (float) 1.0);

        // 查询
        SearchResponse resp = this.client.prepareSearch(EsConstant.bank_index)
            .setQuery(
                QueryBuilders.multiMatchQuery(name).fields(fields)
            )
            .setFrom(0).setSize(10)
            .get();
        return ElasticsearchUtil.converterDocToVo(resp, BankCustomerVo.class);
    }

    /**
     * 查询不能年龄段的客户
     *
     * @return {@link KeyValueVo}
     */
    @Override
    public List<KeyValueVo> groupByAgeRange() {

        // 年龄范围聚合
        RangeAggregationBuilder agg = AggregationBuilders.range("agg")
            .field(EsConstant.bank_index_field_age)
            .addUnboundedTo(30)
            .addRange(30, 50)
            .addUnboundedFrom(50);

        // 查询
        SearchResponse resp = this.client.prepareSearch(EsConstant.bank_index)
            .addAggregation(agg)
            .setSize(0)
            .get();
        return ElasticsearchUtil.converterAggToVo(resp, KeyValueVo.class, "agg");
    }

    /**
     * 范围聚合求账户平均存款
     *
     * @return
     */
    @Override
    public List aggAvgBalance() {

        // 年龄范围聚合
        RangeAggregationBuilder agg = AggregationBuilders.range("agg")
            .field(EsConstant.bank_index_field_age)
            .addUnboundedTo(30)
            .addRange(30, 40)
            .addUnboundedFrom(40)
            .subAggregation(
                AggregationBuilders.terms("gender")
                    .field("gender.keyword")
                    .subAggregation(
                        AggregationBuilders.avg("avg")
                        .field("balance")
                    )
            );

        SearchResponse resp = this.client.prepareSearch(EsConstant.bank_index)
            .addAggregation(agg)
            .setSize(0)
            .get();

        return ElasticsearchUtil.converterAggToVo(resp, KeyValueVo.class, "agg");
    }
}
