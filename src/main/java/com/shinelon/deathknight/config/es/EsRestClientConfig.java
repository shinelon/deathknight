package com.shinelon.deathknight.config.es;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

@Configuration
@ConditionalOnProperty(prefix = "enable", name = "es", havingValue = "true", matchIfMissing = false)
public class EsRestClientConfig extends AbstractElasticsearchConfiguration {

    @Bean
    public ElasticsearchDataAutoConfiguration elasticsearchDataAutoConfiguration() {
        return new ElasticsearchDataAutoConfiguration();
    }

    @Override
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration =
                ClientConfiguration.builder().connectedTo("localhost:9200").build();
        return RestClients.create(clientConfiguration).rest();
    }
}
