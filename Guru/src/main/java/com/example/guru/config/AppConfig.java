package com.example.guru.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.guru.util.CheckUtils;
import com.example.guru.util.CommonUtils;

/**
 * アプリケーション全体の設定クラス。
 * このクラスは、アプリケーションで使用する共通ユーティリティやセキュリティ関連のBeanを定義します。
 * 
 * @version
 * @author kota
 * @since 2025-03-19
 */
@Configuration
public class AppConfig {

    /**
     * CommonUtilsのBeanを定義します。
     * 共通操作に関するユーティリティを提供します。
     * 
     * @return CommonUtils 共通操作ユーティリティのインスタンス
     */
    @Bean
    public CommonUtils commonUtils() {
        return new CommonUtils();
    }
    
    /**
     * CheckUtilsのBeanを定義します。
     * 入力値のチェックや検証に関するユーティリティを提供します。
     * 
     * @return CheckUtils チェックユーティリティのインスタンス
     */
    @Bean
    public CheckUtils checkUtils() {
        return new CheckUtils();
    }
    
    /**
     * PasswordEncoderのBeanを定義します。
     * パスワードのハッシュ化に使用するBCryptPasswordEncoderを提供します。
     * 
     * @return PasswordEncoder パスワードエンコーダーのインスタンス
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}