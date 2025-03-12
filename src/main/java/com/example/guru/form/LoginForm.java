package com.example.guru.form; // パッケージ名

/**
 * ログインフォームクラス
 * @author kota
 * @since 2023-10-01
 */
public class LoginForm {
	
    private String userId; // ユーザーID

    private String password; // パスワード

    // ユーザーIDのgetter
    public String getUserId() {
        return userId;
    }

    // ユーザーIDのsetter
    public void setUserId(String userId) {
        this.userId = userId;
    }

    // パスワードのgetter
    public String getPassword() {
        return password;
    }

    // パスワードのsetter
    public void setPassword(String password) {
        this.password = password;
    }
}