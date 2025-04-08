package com.example.guru.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

/**
 * 各種チェック処理を提供するユーティリティクラス。
 * 文字列の空白チェックや郵便番号の形式チェックなどを行います。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-19
 */
public class CheckUtils {
	
    /**
     * メールアドレスの形式を定義する正規表現パターン。
     * 例: "12345@gmail.com"
     */
	private static final Pattern MAIL_ADDRESS_PATTERN = Pattern.compile("^([a-zA-Z0-9])+([a-zA-Z0-9\\._-])*@([a-zA-Z0-9_-])+([a-zA-Z0-9\\._-]+)+$");

    /**
     * 郵便番号の形式（3桁-4桁）を定義する正規表現パターン。
     * 例: "123-4567"
     */
    private static final Pattern ZIP_CODE_PATTERN = Pattern.compile("^[0-9]{7}$");
    
    /**
     * 半角英数字を定義する正規表現パターン。
     * 例: "gTgw43"
     */
    private static final String HALF_SIZE_NUM = "^[0-9a-zA-Z]+$";

    /**
     * 文字列が空白以外であるかをチェックします。
     * 
     * @param str 入力文字列（null可）
     * @return 空白以外の文字列であればtrue、そうでなければfalse
     */
    public static boolean isNotBlank(String str) {
    	// nullを空文字に変換し、トリム後の長さが0より大きいかを確認
        return CommonUtils.nullToEmpty(str).trim().length() > 0;
    }
    
    /**
     * 文字列のバイト数が指定数以下であるかをチェックします。
     * 
     * @param str 入力文字列（null可）
     * @param byteNum バイト数
     * @return 指定バイト数以下の文字列であればtrue、そうでなければfalse
     */
    public static boolean isByteNum(String str, int byteNum) {
    	if(str == null) {
    		return false;
    	}
        try {
			return str.getBytes("UTF-8").length <= byteNum;
		} catch (UnsupportedEncodingException e) {
			return false;
		}
    }

    /**
     * 郵便番号が正しい形式（ハイフンなし7桁）であるかを判定します。
     * 
     * @param zipCode 郵便番号
     * @return 正しい形式であればtrue、そうでなければfalse
     */
    public static boolean isValidZipCode(String zipCode) {
    	// 正規表現パターンに一致するかを確認
        return !ZIP_CODE_PATTERN.matcher(zipCode).matches();
    }
    
    /**
     * メールアドレスが正しい形式であるかを判定します。
     * 
     * @param str メールアドレス
     * @return 正しい形式であればtrue、そうでなければfalse
     */
    public static boolean isValidMailAddress(String str) {
    	// 正規表現パターンに一致するかを確認
        return MAIL_ADDRESS_PATTERN.matcher(str).matches();
    }
    
    /**
     * 文字列が半角英数字であるかを判定します。
     * 
     * @param str 入力文字列
     * @return 正しい形式であればtrue、そうでなければfalse
     */
    public static boolean isHalfSizeNum(String str) {
    	if(str == null) {
    		return false;
    	}
    	
    	// 正規表現パターンに一致するかを確認
        return str.matches(HALF_SIZE_NUM);
    }
}