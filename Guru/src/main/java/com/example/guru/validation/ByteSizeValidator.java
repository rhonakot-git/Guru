package com.example.guru.validation;

import java.nio.charset.Charset;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * {@link ByteSize} アノテーションのバリデーションロジックを実装するクラス。
 * 文字列が指定されたバイトサイズを超えないかを検証します。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-19
 */
public class ByteSizeValidator implements ConstraintValidator<ByteSize, String> {

    private int max;       // 最大バイト数
    private String charset; // 文字セット

    /**
     * バリデーターの初期化。
     * アノテーションから最大バイト数と文字セットを取得します。
     * 
     * @param constraintAnnotation {@link ByteSize} アノテーション
     */
    @Override
    public void initialize(ByteSize constraintAnnotation) {
        this.max = constraintAnnotation.max();          // 最大バイト数を設定
        this.charset = constraintAnnotation.charset();  // 文字セットを設定
    }

    /**
     * 文字列が指定されたバイトサイズを超えないかを検証します。
     * 
     * @param value 検証する文字列
     * @param context バリデーションコンテキスト
     * @return バイトサイズが max 以下であれば true、そうでなければ false
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;  // null は許可（必要に応じて @NotNull と併用）
        }
        byte[] bytes = value.getBytes(Charset.forName(charset));  // 文字列をバイト配列に変換
        return bytes.length <= max;  // バイト数が max 以下かを確認
    }
}