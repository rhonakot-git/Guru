package com.example.guru.form;

/**
 * ログインフォームクラス。
 * ログイン画面で使用するフォームです。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-19
 */
public class LoginForm {
    
	/** ユーザーID */
    private String userId;
    
    /** パスワード */
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