package com.example.guru.service;

import java.util.List;

import com.example.guru.dto.UserReportsDto;

/**
 * ユーザーレポート出力の操作を提供するサービスインターフェース。
 * ユーザーの検索の取得をサポートします。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-04-07
 */
public interface UserReportsService {

    /**
     * すべてのユーザー情報を取得します。
     * 
     * @return ユーザーレポート出力結果
     */
	List<UserReportsDto> findAllUser();
}