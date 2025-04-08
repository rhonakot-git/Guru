package com.example.guru.service;

import java.util.List;

import com.example.guru.entity.MMenu;
import com.example.guru.entity.MMenuCategory;
import com.example.guru.entity.MUser;
import com.example.guru.form.NewsApiForm;

/**
 * メニューおよびユーザー関連のサービスを提供するインターフェース。
 * ユーザー情報、メニュー権限、ニュースAPIの取得をサポートします。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-19
 */
public interface MenuService {

    /**
     * ユーザーIDに基づいてユーザー情報を取得します。
     * 
     * @param userId ユーザーID
     * @return ユーザー情報
     */
    MUser getUserInfo(String userId);

    /**
     * ロールIDに基づいてアクセス可能なメニューIDのリストを取得します。
     * 
     * @param roleId ロールID
     * @return アクセス可能なメニューIDのリスト
     */
    List<String> getAccessibleMenuId(String roleId);

    /**
     * アクセス可能なメニューIDに基づいて、関連するメニューカテゴリを取得します。
     * 
     * @param accessibleMenuIds アクセス可能なメニューIDのリスト
     * @return アクセス可能なメニューカテゴリのリスト
     */
    List<MMenuCategory> getAccessibleCategories(List<String> accessibleMenuIds);

    /**
     * 指定されたカテゴリIDとアクセス可能なメニューIDに基づいて、メニューを取得します。
     * 
     * @param categoryId カテゴリID
     * @param accessibleMenuIds アクセス可能なメニューIDのリスト
     * @return 指定カテゴリのアクセス可能なメニューのリスト
     */
    List<MMenu> getMenusByCategoryAndRole(String categoryId, List<String> accessibleMenuIds);

    /**
     * ニュースAPIから最新のニュースデータを取得します。
     * 
     * @return ニュースデータ
     */
    NewsApiForm getNewsApi();
}