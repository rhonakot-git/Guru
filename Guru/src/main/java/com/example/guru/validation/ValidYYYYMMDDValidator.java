package com.example.guru.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * {@link ValidYYYYMMDD} アノテーションのバリデーションロジックを実装するクラス。
 * 文字列がyyyyMMdd形式の有効な日付であるかを検証します。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-28
 */
public class ValidYYYYMMDDValidator implements ConstraintValidator<ValidYYYYMMDD, String> {
	
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * 文字列がyyyyMMdd形式の有効な日付であるかを検証します。
     * 
     * @param value 検証する文字列
     * @param context バリデーションコンテキスト
     * @return yyyyMMdd形式で有効な日付であれば true、そうでなければ false
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true; // nullチェックは別のアノテーション（例: @NotNull）に任せる
        }
        try {
            LocalDate.parse(value, FORMATTER); // yyyyMMdd形式でパース可能か確認
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}