package com.example.guru.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.guru.client.NewsApiClient;
import com.example.guru.entity.MMenu;
import com.example.guru.entity.MMenuCategory;
import com.example.guru.entity.MUser;
import com.example.guru.form.NewsApiForm;
import com.example.guru.repository.MMenuCategoryRepository;
import com.example.guru.repository.MMenuRepository;
import com.example.guru.repository.MRoleMenuAuthorityRepository;
import com.example.guru.repository.MUserRepository;

/**
 * {@link MenuService} の実装クラス。
 * ユーザー情報、メニュー権限、ニュースAPIの取得処理を実装します。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-19
 */
@Service
public class MenuServiceImpl implements MenuService {

	// ロガー
    private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

    @Autowired
    private MUserRepository mUserRepository;
    
    @Autowired
    private MMenuRepository menuRepository;
    
    @Autowired
    private MMenuCategoryRepository categoryRepository;
    
    @Autowired
    private MRoleMenuAuthorityRepository authorityRepository;
    
    @Autowired
    private NewsApiClient newsApiClient;

    @Override
    public MUser getUserInfo(String userId) {
    	// ユーザーIDでユーザー情報を取得
        return mUserRepository.findByUserId(userId);
    }

    @Override
    public List<String> getAccessibleMenuId(String roleId) {
    	// ロールIDでアクセス可能なメニューIDを取得
        return authorityRepository.findMenuIdsByRoleId(roleId);
    }

    @Override
    public List<MMenuCategory> getAccessibleCategories(List<String> accessibleMenuIds) {
    	// メニューIDでカテゴリを取得
        return categoryRepository.findCategoriesByMenuIds(accessibleMenuIds);
    }

    @Override
    public List<MMenu> getMenusByCategoryAndRole(String categoryId, List<String> accessibleMenuIds) {
    	// カテゴリとメニューIDでメニューを取得
        return menuRepository.findMenusByCategoryAndIds(categoryId, accessibleMenuIds);
    }

    @Override
    public NewsApiForm getNewsApi() {
    	
    	// ニュース取得開始をログに記録
        logger.info("Starting news fetch");
        
        // 今日の日付を取得
        LocalDate today = LocalDate.now();
        
        // 1週間前の日付を計算
        LocalDate oneWeekAgo = today.minusDays(7);
        
        // 日付フォーマッタ
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        // 1週間前の日付をフォーマット
        String fromDate = oneWeekAgo.format(formatter);
        
        // 今日の日付をフォーマット
        String toDate = today.format(formatter);
        
        // ニュースAPIからデータを取得
        return newsApiClient.fetchNews(fromDate, toDate)  
                .doOnNext(news -> logger.info("Successfully fetched news"))  // 成功時にログ記録
                .block();  // Monoをブロックして直接値を取得
    }
}