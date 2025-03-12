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

@Service
public class MenuServiceImpl implements MenuService {
	
	private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class); // ロギング用

	@Autowired
    private MUserRepository mUserRepository;
    @Autowired
    private MMenuRepository menuRepository;
    @Autowired
    private MMenuCategoryRepository categoryRepository;
    @Autowired
    private MRoleMenuAuthorityRepository authorityRepository;
    @Autowired
    private NewsApiClient newsApiClient; // News APIクライアント
    
    @Override
    public MUser getUserInfo(String userId) {
        return mUserRepository.findByUserId(userId);
    }

    @Override
    public List<String> getAccessibleMenuIds(String roleId) {
        return authorityRepository.findMenuIdsByRoleId(roleId);
    }

    @Override
    public List<MMenuCategory> getAccessibleCategories(List<String> accessibleMenuIds) {
        return categoryRepository.findCategoriesByMenuIds(accessibleMenuIds);
    }

    @Override
    public List<MMenu> getMenusByCategoryAndRole(String categoryId, List<String> accessibleMenuIds) {
        return menuRepository.findMenusByCategoryAndIds(categoryId, accessibleMenuIds);
    }
    
    @Override
    public NewsApiForm getNewsApi() {
        logger.info("Starting news fetch");
        LocalDate today = LocalDate.now();
        LocalDate oneWeekAgo = today.minusDays(7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fromDate = oneWeekAgo.format(formatter);
        String toDate = today.format(formatter);
        return newsApiClient.fetchNews(fromDate, toDate)
                .doOnNext(news -> logger.info("Successfully fetched news"))
                .block(); // Monoを直接値に変換
    }
}