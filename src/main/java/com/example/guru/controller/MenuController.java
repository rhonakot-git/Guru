package com.example.guru.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.guru.entity.MMenu;
import com.example.guru.entity.MMenuCategory;
import com.example.guru.entity.MUser;
import com.example.guru.form.NewsApiForm;
import com.example.guru.service.MenuService;

@Controller
public class MenuController {
	
	private static final Logger logger = LoggerFactory.getLogger(MenuController.class); // ロギング用

    @Autowired
    private MenuService menuService;

    @GetMapping("/menu")
    public String showMenu(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (auth != null && auth.getPrincipal() instanceof User) {
            user = (User) auth.getPrincipal();
        }

        MUser mUser = menuService.getUserInfo(user.getUsername());

        model.addAttribute("loginName", mUser.getUserName()); // ログイン名をモデルに追加

        // アクセス可能なメニューIDを取得
        List<String> accessibleMenuIds = menuService.getAccessibleMenuIds(mUser.getRoleId());
        // アクセス可能なカテゴリを取得
        List<MMenuCategory> categories = menuService.getAccessibleCategories(accessibleMenuIds);
        model.addAttribute("categories", categories);

        // カテゴリごとのメニューをマップとして取得
        Map<String, List<MMenu>> menusByCategory = new HashMap<>();
        for (MMenuCategory category : categories) {
            List<MMenu> menus = menuService.getMenusByCategoryAndRole(category.getCategoryId(), accessibleMenuIds);
            menusByCategory.put(category.getCategoryId(), menus);
        }
        model.addAttribute("menus", menusByCategory);

        return "menu";
    }
    
    @GetMapping("/news")
    @ResponseBody
    public NewsApiForm getNews() {
        logger.info("Fetching news via /news endpoint");
        return menuService.getNewsApi(); // ここは後で説明するけど一旦そのまま
    }
}