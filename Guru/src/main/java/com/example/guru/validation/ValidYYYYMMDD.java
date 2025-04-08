package com.example.guru.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * 日付がyyyyMMdd形式であることを検証するカスタムバリデーションアノテーション。
 * 文字列が正しい日付形式であるかをチェックします。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-28
 */
@Constraint(validatedBy = ValidYYYYMMDDValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidYYYYMMDD {
	
	/**
     * バリデーション失敗時のエラーメッセージ。
     * デフォルトは "日付はyyyyMMDD形式で入力してください。" です。
     * 
     * @return エラーメッセージ
     */
    String message() default "日付はyyyyMMDD形式で入力してください。";
    
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
}