package com.example.guru;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * アプリケーションのエントリーポイント。
 * Spring Bootアプリケーションを起動します。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-19
 */
@SpringBootApplication
@EnableAsync
public class GuruApplication {

    /**
     * アプリケーションのメインメソッド。
     * Spring Bootアプリケーションを起動します。
     * 
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {
    	// アプリケーションを起動
        SpringApplication.run(GuruApplication.class, args);
    }
}