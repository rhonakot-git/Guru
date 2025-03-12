package com.example.guru.validation;

import java.nio.charset.Charset;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ByteSizeValidator implements ConstraintValidator<ByteSize, String> {
    private int max;
    private String charset;

    @Override
    public void initialize(ByteSize constraintAnnotation) {
        this.max = constraintAnnotation.max();
        this.charset = constraintAnnotation.charset();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // nullは許可（必要に応じて@NotNullと併用）
        }
        byte[] bytes = value.getBytes(Charset.forName(charset));
        return bytes.length <= max;
    }
}