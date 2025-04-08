package com.example.guru.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.example.guru.validation.ByteSize;

public class UserForm {
    private String userId;

    @NotBlank(message = "ユーザー名は必須です")
    @ByteSize(max = 100, message = "ユーザー名は100バイト以内で入力してください", charset = "UTF-8")
    private String userName;

    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "パスワードは半角英数字のみで入力してください")
    @Length(max = 10, message = "パスワードは10文字以内で入力してください")
    private String password;

    private String confirmPassword; // パスワード確認用

    @NotBlank(message = "ロールIDは必須です")
    private String roleId;

    @NotBlank(message = "性別は必須です")
    private String gender;

    @Email(message = "有効なメールアドレスを入力してください")
    @Length(max = 100, message = "メールアドレスは100文字以内で入力してください")
    private String email;

    @NotBlank(message = "郵便番号は必須です")
    @Length(max = 7, message = "郵便番号は7文字以内で入力してください")
    private String postalCode;

    @NotBlank(message = "住所1は必須です")
    @ByteSize(max = 150, message = "住所1は150バイト以内で入力してください", charset = "UTF-8")
    private String address1;

    @ByteSize(max = 150, message = "住所2は150バイト以内で入力してください", charset = "UTF-8")
    private String address2;

    @ByteSize(max = 1000, message = "備考は1000バイト以内で入力してください", charset = "UTF-8")
    private String remarks;

    // ゲッターとセッター
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}