package com.lwb.service.impl;

import com.google.common.collect.Maps;
import com.lwb.entity.elasticsearch.EsConstant;
import com.lwb.service.BankCustomerService;
import com.lwb.utils.ElasticsearchUtil;
import com.lwb.vo.BankCustomerVo;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
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
     * @return
     */
    @Override
    public List<BankCustomerVo> fullTextSearchByName(String name) {

        // 设置要查询的字段，以及字段的重要性
        Map<String, Float> fields = Maps.newHashMap();
        fields.put(EsConstant.bank_index_field_firstname, (float) 2.0);
        fields.put(EsConstant.bank_index_field_lastname, (float) 1.0);

        SearchResponse resp = this.client.prepareSearch(EsConstant.bank_index)
            .setQuery(
                QueryBuilders.multiMatchQuery(name).fields(fields)
            )
            .setFrom(0).setSize(10)
            .get();
        return ElasticsearchUtil.converterVo(resp, BankCustomerVo.class);
    }
}
