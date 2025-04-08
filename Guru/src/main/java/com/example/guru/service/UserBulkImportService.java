package com.example.guru.service;

import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.example.guru.dto.UserBulkImportSearchDetail;
import com.example.guru.entity.ImportHistory;
import com.example.guru.form.UserBulkImportSearchForm;

/**
 * ユーザ一括インポートのサービスを提供するインターフェース。
 * CSV情報を一括でDB登録するのをサポートします。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-24
 */
public interface UserBulkImportService {
	
    /**
     * 履歴情報を登録します。
     * 
     * @param file 取込ファイル
     * @param bindingResult バリデーション結果
     * @return ユーザー情報
     */
    ImportHistory startImport(MultipartFile file, BindingResult bindingResult);
    
    /**
     * 一括インポート処理を開始します。
     * 
     * @param importId 取込番号
     * @param filePath 取込ファイル
     * @param currentUser 処理ユーザ
     */
    void processImportAsync(Long importId, String filePath, String currentUser);
    
    /**
     * インポート履歴検索条件に基づいて、ページングされたインポート履歴詳細情報を取得します。
     * 
     * @param searchForm インポート履歴検索フォーム
     * @param page ページ番号（1-based）
     * @param size ページサイズ
     * @return ページングされたインポート履歴詳細情報
     */
    Page<UserBulkImportSearchDetail> getImportHistoryPage(UserBulkImportSearchForm searchForm, int page, int size);
}