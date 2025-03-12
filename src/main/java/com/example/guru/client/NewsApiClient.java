package com.example.guru.client;

import java.net.URI;
import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.guru.config.NewsApiProperties;
import com.example.guru.form.NewsApiForm;

import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Component
public class NewsApiClient {

    private static final Logger logger = LoggerFactory.getLogger(NewsApiClient.class);
    private final WebClient webClient;
    private final NewsApiProperties props;

    public NewsApiClient(WebClient newsWebClient, NewsApiProperties props) {
        this.webClient = newsWebClient;
        this.props = props;
    }

    public Mono<NewsApiForm> fetchNews(String fromDate, String toDate) {
        logger.info("ニュースを取得します: fromDate={}, toDate={}", fromDate, toDate);
        
        try {
        	Thread.sleep(3000); // 10秒(1万ミリ秒)間だけ処理を止める
        } catch (InterruptedException e) {
        }

        return webClient.get()
                .uri(uriBuilder -> {
                    URI uri = uriBuilder
                            .queryParam("q", props.getQuery())
                            .queryParam("from", fromDate)
                            .queryParam("to", toDate)
                            .queryParam("language", props.getLanguage())
                            .queryParam("sortBy", "publishedAt")
                            .queryParam("pageSize", props.getPageSize())
                            .queryParam("apiKey", props.getKey())
                            .build();
                    logger.debug("生成されたURI: {}", uri.toString());
                    return uri;
                })
                .retrieve()
                .bodyToMono(NewsApiForm.class)
                .doOnNext(news -> logger.info("記事を{}件取得しました", news.getArticles() != null ? news.getArticles().size() : 0))
                .doOnError(error -> logger.error("ニュース取得エラー: {}", error.getMessage()))
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
                        .filter(throwable -> throwable instanceof RuntimeException))
                .onErrorResume(error -> {
                    logger.warn("エラー発生のため空のNewsApiFormを返します: {}", error.getMessage());
                    return Mono.just(new NewsApiForm());
                });
    }
}