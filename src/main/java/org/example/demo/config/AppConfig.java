package org.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

/**
 * General app configuration.
 */
@Configuration
@EnableJpaRepositories(basePackages = "org.example.demo")
@EnableCaching
public class AppConfig {

    @Value("${external-api.region.url:'localhost:8080'}")
    private String url;

    @Bean
    public WebClient webClient() {
        HttpClient httpClient = HttpClient.create().responseTimeout(Duration.ofSeconds(7));
        return WebClient.builder()
                .baseUrl(url)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
