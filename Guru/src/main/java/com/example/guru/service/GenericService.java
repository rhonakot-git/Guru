package com.example.guru.service;

import java.util.List;

import com.example.guru.entity.MGeneric;

/**
 * 汎用マスタデータを操作するためのサービスインターフェース。
 * コンボボックスなどに使用されるマスターデータ（カテゴリごとのコードと名前）を提供します。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-19
 */
public interface GenericService {

    /**
     * 指定されたカテゴリーに属するデータを取得します。
     * 
     * @param category カテゴリー
     * @return 指定カテゴリーのデータリスト
     */
    List<MGeneric> getCategoryMasterData(String category);
}