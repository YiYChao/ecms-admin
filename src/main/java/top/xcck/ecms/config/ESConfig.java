package top.xcck.ecms.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
/**
 *  @Description: ElasticSearch 配置类，配置主机和端口号
 *  @author YiYChao
 *  @Date: 2020/4/1 23:51
 *  @version V1.0
 */
@Configuration
public class ESConfig extends AbstractElasticsearchConfiguration {

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {

        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
            .connectedTo("192.168.240.131:9200")
            .build();

        return RestClients.create(clientConfiguration).rest();
    }
}