package com.example.guru.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.guru.entity.MMenu;
import com.example.guru.entity.MMenuCategory;
import com.example.guru.entity.MUser;
import com.example.guru.service.MenuService;

/**
 * 全てのコントローラーで共通して使用される属性を設定する抽象コントローラークラス。
 * ログイン情報やメニュー情報をモデルに追加します。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-19
 */
@ControllerAdvice
public abstract class BaseController {

	// メニューサービス
    @Autowired
    protected MenuService menuService;

    /**
     * 認証情報に基づいて、ログイン名やアクセス可能なメニュー情報をモデルに追加します。
     * 
     * @param model ビューに渡すモデル
     * @param authentication 現在の認証情報
     */
    @ModelAttribute
    public void setCommonAttributes(Model model, Authentication authentication) {
    	
    	// 認証情報が存在する場合
        if (authentication != null && authentication.getPrincipal() instanceof User) {
        	
            User user = (User) authentication.getPrincipal();
            
            // ユーザー名からユーザー情報を取得
            MUser mUser = menuService.getUserInfo(user.getUsername());  
            
            // ログイン名をモデルに追加
            model.addAttribute("loginName", mUser.getUserName());

            // ロールIDに基づくアクセス可能なメニューIDを取得
            List<String> accessibleMenuIds = menuService.getAccessibleMenuId(mUser.getRoleId());
            
            // アクセス可能なカテゴリを取得
            List<MMenuCategory> categories = menuService.getAccessibleCategories(accessibleMenuIds);
            
            // カテゴリをモデルに追加
            model.addAttribute("categories", categories);

            // カテゴリごとのメニューを取得
            Map<String, List<MMenu>> menusByCategory = new HashMap<>();
            for (MMenuCategory category : categories) {
                List<MMenu> menus = menuService.getMenusByCategoryAndRole(category.getCategoryId(), accessibleMenuIds);
                menusByCategory.put(category.getCategoryId(), menus);
            }
            
            // メニューをモデルに追加
            model.addAttribute("menus", menusByCategory);
        }
    }
}