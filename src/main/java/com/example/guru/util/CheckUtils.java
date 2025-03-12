package com.example.guru.util;

import java.util.regex.Pattern;

public class CheckUtils {
	// 郵便番号の形式（3桁-4桁）を定義
    private static final Pattern ZIP_CODE_PATTERN = Pattern.compile("\\d{3}-\\d{4}");
    
    /**
     * 文字列が空白以外かをチェック
     * @param str 入力文字列（null可）
     * @return 空白以外の文字列であればtrue
     */
    public static boolean isNotBlank(String str) {
        return StringUtils.nullToEmpty(str).trim().length() > 0;
    }

    // 郵便番号が正しい形式かどうかを判定
    public static boolean isValidZipCode(String zipCode) {
        return ZIP_CODE_PATTERN.matcher(zipCode).matches();
    }
}
