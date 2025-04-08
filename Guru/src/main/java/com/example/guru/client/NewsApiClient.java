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

/**
 * News APIクライアントクラス。
 * News APIからニュース記事を取得するための機能を提供します。
 * WebClientを使用してAPIリクエストを送信し、データを返却します。
 * 
 * @version 1.0.0.0
 * @author kota
 * @since 2026-04-29
 */
@Component
public class NewsApiClient {

    // ロガー
    private static final Logger logger = LoggerFactory.getLogger(NewsApiClient.class);
    
    // WebClient
    private final WebClient webClient;
    
    // NewsAPIの設定プロパティ
    private final NewsApiProperties props;

    /**
     * コンストラクタ。
     * WebClientとNewsApiPropertiesを注入して初期化します。
     * 
     * @param newsWebClient NewsAPI用のWebClientインスタンス
     * @param props NewsAPIの設定プロパティ
     */
    public NewsApiClient(WebClient newsWebClient, NewsApiProperties props) {
        this.webClient = newsWebClient;
        this.props = props;
    }

    /**
     * 指定された日付範囲のニュース記事を取得します。
     * 
     * @param fromDate 取得開始日（yyyy-MM-dd形式）
     * @param toDate 取得終了日（yyyy-MM-dd形式）
     * @return Mono<NewsApiForm> ニュース記事のリスト
     */
    public Mono<NewsApiForm> fetchNews(String fromDate, String toDate) {
        
        logger.info("ニュースを取得します: fromDate={}, toDate={}", fromDate, toDate);

        // WebClientを使用してGETリクエストを送信
        return webClient.get()
                .uri(uriBuilder -> {
                    // URIを構築し、クエリパラメータを追加
                    URI uri = uriBuilder
                            .queryParam("q", props.getQuery()) 				// 国
                            .queryParam("from", fromDate) 					// 開始日
                            .queryParam("to", toDate) 						// 終了日
                            .queryParam("language", props.getLanguage()) 	// 言語
                            .queryParam("sortBy", "publishedAt") 			// ソート基準
                            .queryParam("pageSize", props.getPageSize()) 	// ページサイズ
                            .queryParam("apiKey", props.getKey()) 			// APIキー
                            .build();
                    logger.debug("生成されたURI: {}", uri.toString());
                    return uri;
                })
                .retrieve() // レスポンスを取得
                .bodyToMono(NewsApiForm.class) // 戻り値の型
                .doOnNext(news -> {
                    // 取得した記事の件数をログに記録
                    int articleCount = (news.getArticles() != null) ? news.getArticles().size() : 0;
                    logger.info("記事を{}件取得しました", articleCount);
                })
                .doOnError(error -> {
                    // エラー発生時にログに記録
                    logger.error("ニュース取得エラー: {}", error.getMessage());
                })
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
                        .filter(throwable -> throwable instanceof RuntimeException)) // 最大3回のリトライ（RuntimeExceptionの場合）
                .onErrorResume(error -> {
                    // エラー発生時に空のNewsApiFormを返す
                    logger.warn("エラー発生のため空のNewsApiFormを返します: {}", error.getMessage());
                    return Mono.just(new NewsApiForm());
                });
    }
}