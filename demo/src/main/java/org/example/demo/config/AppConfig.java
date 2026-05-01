package org.example.demo.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.netty.http.client.HttpClient;
import java.time.Duration;

@Configuration
@EnableKafkaStreams
@ConfigurationProperties("external.api")
public class AppConfig {

    private String url;
    public static final String METEOROLOGY_TOPIC = "meteorology";
    public static final String STATS_TOPIC = "stats";

    @Bean
    public WebClient webClient() {
        HttpClient httpClient = HttpClient.create().responseTimeout(Duration.ofSeconds(7));
        return WebClient.builder()
                .baseUrl(url)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    @Bean
    public NewTopic meteorologyTopic() {
        return TopicBuilder.name(METEOROLOGY_TOPIC)
                .partitions(1)
                .replicas(2)
                .build();
    }

    @Bean
    public NewTopic statsTopic() {
        return TopicBuilder.name(STATS_TOPIC)
                .partitions(1)
                .replicas(2)
                .build();
    }
}
