package com.example.guru.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * 文字列のバイトサイズを制限するカスタムバリデーションアノテーション。
 * 指定された文字セットでのバイト数が最大値を超えないことを検証します。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-19
 */
@Documented
@Constraint(validatedBy = ByteSizeValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ByteSize {

    /**
     * バリデーション失敗時のエラーメッセージ。
     * デフォルトは "バイト数が制限を超えています" です。
     * 
     * @return エラーメッセージ
     */
    String message() default "バイト数が制限を超えています";

    /**
     * バリデーショングループ。
     * デフォルトは空です。
     * 
     * @return バリデーショングループ
     */
    Class<?>[] groups() default {};

    /**
     * ペイロード。
     * デフォルトは空です。
     * 
     * @return ペイロード
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * 許可される最大バイト数。
     * 
     * @return 最大バイト数
     */
    int max();

    /**
     * バイトサイズを計算する際に使用する文字セット。
     * デフォルトは "UTF-8" です。
     * 
     * @return 文字セット
     */
    String charset() default "UTF-8";
}