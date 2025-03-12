package com.example.guru.util;

public class StringUtils {

    /**
     * nullを空文字に変換する
     * @param str 入力文字列（null可）
     * @return 変換後の文字列（nullの場合は空文字）
     */
    public static String nullToEmpty(String str) {
        return str == null ? "" : str;
    }
}