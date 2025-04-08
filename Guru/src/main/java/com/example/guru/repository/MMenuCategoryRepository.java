package com.example.guru.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.guru.entity.MMenuCategory;

/**
 * メニューカテゴリのリポジトリ。
 * メニューカテゴリのデータ操作をサポートします。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-19
 */
@Repository
public interface MMenuCategoryRepository extends JpaRepository<MMenuCategory, String> {

    /**
     * 指定されたメニューIDに関連するメニューカテゴリを取得します。
     * 
     * @param menuIds メニューIDのリスト
     * @return 関連するメニューカテゴリのリスト
     */
	@Query(value = "SELECT DISTINCT MMC.CATEGORY_ID, MMC.CATEGORY_NAME, MMC.CREATE_USER, MMC.UPDATE_USER, MMC.CREATE_DATE_TIME, MMC.UPDATE_DATE_TIME, MMC.ICON_TAG FROM M_MENU_CATEGORY MMC JOIN M_MENU MM ON MMC.CATEGORY_ID = MM.CATEGORY_ID WHERE MM.MENU_ID IN :menuIds", nativeQuery = true)
    List<MMenuCategory> findCategoriesByMenuIds(List<String> menuIds);
}