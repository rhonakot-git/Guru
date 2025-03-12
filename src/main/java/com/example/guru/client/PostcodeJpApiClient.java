package com.example.guru.client;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.guru.config.PostcodeJpApiProperties;
import com.example.guru.form.PostcodeJpForm.AddressData;

import reactor.core.publisher.Mono;

@Component
public class PostcodeJpApiClient {

    private static final Logger logger = LoggerFactory.getLogger(PostcodeJpApiClient.class);
    private final WebClient webClient;
    private final PostcodeJpApiProperties props;

    public PostcodeJpApiClient(WebClient postcodeJpWebClient, PostcodeJpApiProperties props) {
        this.webClient = postcodeJpWebClient;
        this.props = props;
    }

    public Mono<List<AddressData>> fetchAddress(String postalCode) {
        logger.info("住所を取得します: postalCode={}", postalCode);

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/{postalCode}")
                        .queryParam("apikey", props.getKey())
                        .build(postalCode))
                .retrieve()
                .bodyToFlux(AddressData.class)  // 配列をFluxとして取得
                .collectList()  // FluxをListに変換
                .doOnNext(addresses -> logger.info("住所データを取得しました: データ件数={}", addresses.size()))
                .doOnError(error -> logger.error("住所取得エラー: {}", error.getMessage()))
                .onErrorResume(error -> {
                    logger.warn("エラー発生のため空のリストを返します: {}", error.getMessage());
                    return Mono.just(List.of());
                });
    }
}