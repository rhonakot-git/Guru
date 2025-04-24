package com.example.guru.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * ログインフォームクラス。
 * ログイン画面で使用するフォームです。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-19
 */
public class LoginForm {
    
	@NotBlank(message = "ユーザーIDは必須です")
    @Size(max = 10, message = "ユーザーIDは10文字以内で入力してください")
    @Pattern(regexp = "[A-Za-z0-9]+", message = "ユーザーIDは半角英数字で入力してください")
    private String userId;

    @NotBlank(message = "パスワードは必須です")
    @Size(max = 10, message = "パスワードは10文字以内で入力してください")
    private String password;
    
    /**
     * ユーザーIDを取得します。
     * 
     * @return ユーザーID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * ユーザーIDを設定します。
     * 
     * @param userId ユーザーID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * パスワードを取得します。
     * 
     * @return パスワード
     */
    public String getPassword() {
        return password;
    }

    /**
     * パスワードを設定します。
     * 
     * @param password パスワード
     */
    public void setPassword(String password) {
        this.password = password;
    }
}