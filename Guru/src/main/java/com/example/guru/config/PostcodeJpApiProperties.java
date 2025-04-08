package com.example.guru.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * PostcodeJP APIの設定を保持するプロパティクラス。
 * 
 * アプリケーションの設定ファイル（例: application.properties や application.yml）から
 * "postcodejp.api" プレフィックスを持つ設定値を読み込みます。
 * 読み込まれた値は、このクラスのフィールドにマッピングされます。
 * 
 * @version
 * @author kota
 * @since 2025-03-19
 */
@Component
@ConfigurationProperties(prefix = "postcodejp.api")
public class PostcodeJpApiProperties {
	
	/** APIのベースURL */
    private String url;
    
    /** APIキー */
    private String key;
    
    /** 接続タイムアウト（ミリ秒） */
    private int connectTimeout;
    
    /** 読み取りタイムアウト（ミリ秒） */
    private int readTimeout;

	/**
	 * url を取得します。
	 *
	 * @return url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * urlを設定します。
	 *
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * key を取得します。
	 *
	 * @return key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * keyを設定します。
	 *
	 * @param key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * connectTimeout を取得します。
	 *
	 * @return connectTimeout
	 */
	public int getConnectTimeout() {
		return connectTimeout;
	}

	/**
	 * connectTimeoutを設定します。
	 *
	 * @param connectTimeout
	 */
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	/**
	 * readTimeout を取得します。
	 *
	 * @return readTimeout
	 */
	public int getReadTimeout() {
		return readTimeout;
	}

	/**
	 * readTimeoutを設定します。
	 *
	 * @param readTimeout
	 */
	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}
}