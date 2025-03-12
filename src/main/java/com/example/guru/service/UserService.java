package com.example.guru.service; // パッケージ名

import java.util.List;

import org.springframework.validation.BindingResult;

import com.example.guru.entity.MUser; // MUser用インポート
import com.example.guru.form.PostcodeJpForm.AddressData;
import com.example.guru.form.UserForm;

/**
 * ユーザーサービスインターフェース
 * @author kota
 * @since 2023-10-01
 */
public interface UserService {
    MUser findByUserId(String userId); // ユーザーIDで検索するメソッド
    
    void registerUser(UserForm userForm, BindingResult bindingResult);
    void updateUser(UserForm userForm, BindingResult bindingResult);
    
    List<AddressData> getAddressByPostalCode(String postalCode); // 新規追加
}