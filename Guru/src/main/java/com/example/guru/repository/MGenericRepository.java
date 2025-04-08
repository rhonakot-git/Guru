package com.example.guru.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.guru.entity.MGeneric;
import com.example.guru.entity.MGenericId;

/**
 * 汎用マスターデータのリポジトリ。
 * カテゴリごとのマスターデータを管理します。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-19
 */
public interface MGenericRepository extends JpaRepository<MGeneric, MGenericId> {

    /**
     * 指定されたカテゴリのマスターデータを並び順で取得します。
     * 
     * @param category マスターデータのカテゴリ
     * @return 指定カテゴリのマスターデータリスト（並び順でソート済み）
     */
    List<MGeneric> findByCategoryOrderBySortOrder(String category);
}