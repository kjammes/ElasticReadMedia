package com.jayesh.elasticsearch.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ElasticConfig {
    @Value("${elastic.username}")
    private String ELASTIC_USERNAME;
    @Value("${elastic.password}")
    private String ELASTIC_PASSWORD;

    @Bean
    public RestClient client() {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(ELASTIC_USERNAME, ELASTIC_PASSWORD));
        HttpHost host = new HttpHost("127.0.0.1", 9200);

        return RestClient.builder(host)
                .setHttpClientConfigCallback(httpClientBuilder -> {
                            httpClientBuilder.disableAuthCaching();
                            return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                        }).build();
    }

    @Bean
    public ElasticsearchClient elasticsearchTemplate() {
        ElasticsearchTransport elasticsearchTransport = new RestClientTransport(client(), new JacksonJsonpMapper());
        return new ElasticsearchClient(elasticsearchTransport);
    }
}
