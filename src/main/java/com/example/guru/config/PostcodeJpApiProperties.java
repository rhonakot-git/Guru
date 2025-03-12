package com.example.guru.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "postcodejp.api")
public class PostcodeJpApiProperties {
    private String url;          // APIのベースURL
    private String key;          // APIキー
    private int connectTimeout;  // 接続タイムアウト（ミリ秒）
    private int readTimeout;     // 読み取りタイムアウト（ミリ秒）

    // ゲッターとセッター
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
    public int getConnectTimeout() { return connectTimeout; }
    public void setConnectTimeout(int connectTimeout) { this.connectTimeout = connectTimeout; }
    public int getReadTimeout() { return readTimeout; }
    public void setReadTimeout(int readTimeout) { this.readTimeout = readTimeout; }
}