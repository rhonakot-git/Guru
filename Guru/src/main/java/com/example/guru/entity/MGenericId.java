package com.example.guru.entity;

import java.io.Serializable;

public class MGenericId implements Serializable {
    private String category;
    private String code;

    // ゲッターとセッター
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
}