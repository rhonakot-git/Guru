package com.example.guru.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = ByteSizeValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ByteSize {
    String message() default "バイト数が制限を超えています";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int max();
    String charset() default "UTF-8";
}