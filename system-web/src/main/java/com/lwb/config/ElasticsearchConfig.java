package com.lwb.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * @author liuweibo
 * @date 2018/7/3
 */
@Configuration
public class ElasticsearchConfig {

    private final Logger logger = LoggerFactory.getLogger(ElasticsearchConfig.class);

    @Value("${es.hosts}")
    private String hosts;

    @Value("${es.cluster}")
    private String cluster;

    public ElasticsearchConfig() {
    }

    @Bean
    public TransportClient transportClient() {
        return this.buildClient();
    }

    private TransportClient buildClient() {
        Settings settings = Settings.builder()
            .put("cluster.name", cluster)
            .put("client.transport.sniff", true)
            .build();
        PreBuiltTransportClient client = new PreBuiltTransportClient(settings);
        String[] hostArr = this.hosts.split(",");
        for (String host : hostArr) {
            String[] ipAndPort = host.split(":");
            try {
                client.addTransportAddresses(
                    new InetSocketTransportAddress(
                        InetAddress.getByName(ipAndPort[0]), Integer.valueOf(ipAndPort[1])
                    )
                );
            } catch (UnknownHostException e) {
                logger.error("not find host: {}", ipAndPort[0]);
            }
            List<DiscoveryNode> nodes = client.listedNodes();
            for (DiscoveryNode node : nodes) {
                logger.info("discover node: {}", node.getHostAddress());
            }
        }
        return client;
    }
}
