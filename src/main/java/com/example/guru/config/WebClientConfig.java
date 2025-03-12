package com.example.guru.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient newsWebClient(WebClient.Builder builder, NewsApiProperties props) {
        return builder
                .baseUrl(props.getUrl())
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, props.getConnectTimeout())
                        .responseTimeout(Duration.ofMillis(props.getReadTimeout()))))
                .build();
    }

    @Bean
    public WebClient postcodeJpWebClient(WebClient.Builder builder, PostcodeJpApiProperties props) {
        return builder
                .baseUrl(props.getUrl())
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, props.getConnectTimeout())
                        .responseTimeout(Duration.ofMillis(props.getReadTimeout()))))
                .build();
    }
}