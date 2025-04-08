package com.example.guru.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.guru.entity.MRoleMenuAuthority;

/**
 * ロールとメニューの権限リポジトリ。
 * ロールごとのメニュー権限を管理します。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-19
 */
@Repository
public interface MRoleMenuAuthorityRepository extends JpaRepository<MRoleMenuAuthority, String> {

    /**
     * 指定されたロールIDに関連するメニューIDのリストを取得します。
     * 
     * @param roleId ロールID
     * @return 関連するメニューIDのリスト
     * @version 1.0
     * @author kota
     * @since 2025-03-19
     */
    @Query(value = "SELECT "
    		+ "         MENU_ID "
    		+ "     FROM "
    		+ "         M_ROLE_MENU_AUTHORITY "
    		+ "     WHERE"
    		+ "         ROLE_ID = :roleId "
    		+ "     ORDER BY"
    		+ "         MENU_ID",
    		nativeQuery = true)
    List<String> findMenuIdsByRoleId(String roleId);
}