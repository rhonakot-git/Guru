package com.example.guru.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;

import com.example.guru.dto.UserSearchDetail;
import com.example.guru.entity.MUser;
import com.example.guru.form.PostcodeJpForm.AddressData;
import com.example.guru.form.UserForm;
import com.example.guru.form.UserSearchForm;

/**
 * ユーザー関連の操作を提供するサービスインターフェース。
 * ユーザーの検索、登録、更新、住所情報の取得をサポートします。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-19
 */
public interface UserService {

    /**
     * ユーザーIDに基づいてユーザー情報を取得します。
     * 
     * @param userId ユーザーID
     * @return ユーザー情報
     */
    MUser findByUserId(String userId);

    /**
     * ユーザー情報を登録します。
     * 
     * @param userForm ユーザー登録フォーム
     * @param bindingResult バリデーション結果
     */
    void registerUser(UserForm userForm, BindingResult bindingResult);

    /**
     * 既存のユーザー情報を更新します。
     * 
     * @param userForm ユーザー更新フォーム
     * @param bindingResult バリデーション結果
     */
    void updateUser(UserForm userForm, BindingResult bindingResult);

    /**
     * 郵便番号に基づいて住所情報を取得します。
     * 
     * @param postalCode 郵便番号
     * @return 住所情報のリスト
     */
    List<AddressData> getAddressByPostalCode(String postalCode);

    /**
     * ユーザー検索条件に基づいて、ページングされたユーザー詳細情報を取得します。
     * 
     * @param userSearchForm ユーザー検索フォーム
     * @param page ページ番号（1-based）
     * @param size ページサイズ
     * @return ページングされたユーザー詳細情報
     */
    Page<UserSearchDetail> getUserDetailPage(UserSearchForm userSearchForm, int page, int size);
    
    /**
     * ユーザー検索条件に基づいて、ユーザー詳細情報を取得します(ページングなし)。
     * 
     * @param userSearchForm ユーザー検索フォーム
     * @return ユーザー詳細情報
     */
    List<UserSearchDetail> getAllUserDetails(UserSearchForm searchForm);
}