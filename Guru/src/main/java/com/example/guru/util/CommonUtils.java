package com.example.guru.util;

/**
 * 文字列操作に関するユーティリティクラス。
 * 文字列の変換や処理をサポートします。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-19
 */
public class CommonUtils {

    /**
     * nullを空文字に変換します。
     * 
     * @param str 入力文字列
     * @return 変換後の文字列（nullの場合は空文字）
     */
    public static String nullToEmpty(String str) {
    	// nullの場合は空文字を返し、それ以外は元の文字列を返す
        return str == null ? "" : str;
    }
}