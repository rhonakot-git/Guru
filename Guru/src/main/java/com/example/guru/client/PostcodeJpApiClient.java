package com.example.guru.client;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.guru.config.PostcodeJpApiProperties;
import com.example.guru.form.PostcodeJpForm.AddressData;

import reactor.core.publisher.Mono;

/**
 * PostcodeJP APIクライアントクラス。
 * このクラスは、PostcodeJP APIを利用して指定された郵便番号に基づく住所情報を取得する機能を提供します。
 * WebClientを活用し、APIリクエストを非同期で処理し、住所データをAddressDataのリストとして返します。
 * 
 * @version
 * @author kota
 * @since 2025-03-19
 */
@Component
public class PostcodeJpApiClient {

    // ロガー
    private static final Logger logger = LoggerFactory.getLogger(PostcodeJpApiClient.class);
    
    // WebClient
    private final WebClient webClient;
    
    // PostcodeJpAPIの設定プロパティ
    private final PostcodeJpApiProperties props;

    /**
     * コンストラクタ。
     * WebClientとPostcodeJpApiPropertiesを注入して初期化します。
     * 
     * @param postcodeJpWebClient PostcodeJpAPI用のWebClientインスタンス
     * @param props PostcodeJpAPIの設定プロパティ
     */
    public PostcodeJpApiClient(WebClient postcodeJpWebClient, PostcodeJpApiProperties props) {
        this.webClient = postcodeJpWebClient;
        this.props = props;
    }

    /**
     * 指定された郵便番号に基づいて住所情報を取得します。
     * 
     * @param postalCode 取得対象の郵便番号(ハイフンなし)
     * @return Mono<List<AddressData>> 住所情報のリスト
     */
    public Mono<List<AddressData>> fetchAddress(String postalCode) {

        logger.info("住所を取得します: postalCode={}", postalCode);

        // WebClientを使用してGETリクエストを送信
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/{postalCode}") 					// 郵便番号をパスとして指定
                        .queryParam("apikey", props.getKey()) 	// APIキー
                        .build(postalCode)) 					// 郵便番号をURIに適用
                .retrieve() // レスポンスを取得
                .bodyToFlux(AddressData.class) // 戻り値の型
                .collectList() // FluxをListに変換
                .doOnNext(addresses -> {
                    // 取得した住所データの件数をログに記録
                    logger.info("住所データを取得しました: データ件数={}", addresses.size());
                })
                .doOnError(error -> {
                    // エラー発生時にログに記録
                    logger.error("住所取得エラー: {}", error.getMessage());
                })
                .onErrorResume(error -> {
                    // エラー発生時に空のリストを返す
                    logger.warn("エラー発生のため空のリストを返します: {}", error.getMessage());
                    return Mono.just(List.of());
                });
    }
}