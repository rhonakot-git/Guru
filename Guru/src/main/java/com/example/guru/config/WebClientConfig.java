package com.example.guru.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import reactor.netty.http.client.HttpClient;

/**
 * アプリケーションで使用する WebClientインスタンスを設定するコンフィグレーションクラス。
 * 
 * 外部API向けに、事前設定された WebClient を提供します。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-19
 */
@Configuration
public class WebClientConfig {

    /**
     * NewsAPI向けに設定された WebClient を生成します。
     * 
     * @param builder WebClient の作成に使用する情報
     * @param props NewsAPIの設定情報を含むプロパティ情報
     * @return NewsAPI用の設定済み WebClient
     */
    @Bean
    public WebClient newsWebClient(WebClient.Builder builder, NewsApiProperties props) {
        return builder
                .baseUrl(props.getUrl())  // プロパティから取得したベースURLを設定
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, props.getConnectTimeout())  // 接続タイムアウトを設定
                        .responseTimeout(Duration.ofMillis(props.getReadTimeout()))))  // レスポンスタイムアウトを設定
                .build();
    }

    /**
     * PostcodeJP API向けに設定された WebClient を生成します。
     * 
     * @param builder WebClient の作成に使用する情報
     * @param props PostcodeJP APIの設定情報を含むプロパティ情報
     * @return PostcodeJP API用の設定済み WebClient
     */
    @Bean
    public WebClient postcodeJpWebClient(WebClient.Builder builder, PostcodeJpApiProperties props) {
        return builder
                .baseUrl(props.getUrl())  // プロパティから取得したベースURLを設定
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, props.getConnectTimeout())  // 接続タイムアウトを設定
                        .responseTimeout(Duration.ofMillis(props.getReadTimeout()))))  // レスポンスタイムアウトを設定
                .build();
    }
}