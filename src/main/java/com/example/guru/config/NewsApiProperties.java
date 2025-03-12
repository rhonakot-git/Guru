package com.example.guru.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "news.api")
public class NewsApiProperties {
    private String url;          // APIのベースURL
    private String key;          // APIキー
    private String query;        // 検索クエリ
    private String language;     // 言語
    private int pageSize;        // 1ページあたりの取得件数
    private int connectTimeout;  // 接続タイムアウト（ミリ秒）
    private int readTimeout;     // 読み取りタイムアウト（ミリ秒）

    // ゲッターとセッター
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
    public String getQuery() { return query; }
    public void setQuery(String query) { this.query = query; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public int getPageSize() { return pageSize; }
    public void setPageSize(int pageSize) { this.pageSize = pageSize; }
    public int getConnectTimeout() { return connectTimeout; }
    public void setConnectTimeout(int connectTimeout) { this.connectTimeout = connectTimeout; }
    public int getReadTimeout() { return readTimeout; }
    public void setReadTimeout(int readTimeout) { this.readTimeout = readTimeout; }
}