package com.example.guru.service;

import java.util.List;

import com.example.guru.entity.MMenu;
import com.example.guru.entity.MMenuCategory;
import com.example.guru.entity.MUser;
import com.example.guru.form.NewsApiForm;

public interface MenuService {
	
	MUser getUserInfo(String userId);
	
    List<String> getAccessibleMenuIds(String roleId);
    List<MMenuCategory> getAccessibleCategories(List<String> accessibleMenuIds);
    List<MMenu> getMenusByCategoryAndRole(String categoryId, List<String> accessibleMenuIds);
    NewsApiForm getNewsApi();
}