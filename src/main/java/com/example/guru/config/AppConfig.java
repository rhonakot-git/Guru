package com.example.guru.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.guru.util.CheckUtils;
import com.example.guru.util.StringUtils;

@Configuration
public class AppConfig {

    @Bean
    public StringUtils stringUtils() { // メソッド名を役割に合わせて変更
        return new StringUtils();
    }
    
    @Bean
    public CheckUtils checkUtils() { // メソッド名を役割に合わせて変更
        return new CheckUtils();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}