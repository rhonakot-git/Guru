package com.example.guru.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.guru.entity.MMenu;

/**
 * メニューのリポジトリ。
 * メニューデータの操作をサポートします。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-19
 */
@Repository
public interface MMenuRepository extends JpaRepository<MMenu, String> {

    /**
     * 指定されたカテゴリIDとメニューIDのリストに該当するメニューを取得します。
     * 
     * @param categoryId カテゴリID
     * @param menuIds メニューIDのリスト
     * @return 指定条件に該当するメニューのリスト
     */
	@Query(value = "SELECT MENU_ID, CATEGORY_ID, MENU_NAME, CREATE_USER, UPDATE_USER, CREATE_DATE_TIME, UPDATE_DATE_TIME, ICON_TAG, MENU_URL FROM M_MENU WHERE CATEGORY_ID = :categoryId AND MENU_ID IN :menuIds ORDER BY CATEGORY_ID, MENU_ID", nativeQuery = true)
    List<MMenu> findMenusByCategoryAndIds(String categoryId, List<String> menuIds);
}