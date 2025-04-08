package com.example.guru.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.guru.form.NewsApiForm;
import com.example.guru.service.MenuService;

/**
 * メニューページを表示するためのコントローラークラス。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-19
 */
@Controller
public class MenuController {

    // メニューサービス
    @Autowired
    private MenuService menuService;

    /**
     * メニューページを表示します。ニュースAPIからデータを取得し、モデルに追加します。
     * 
     * @param model ビューに渡すモデル
     * @return レイアウトテンプレートのビュー名
     */
    @GetMapping("/menu")
    public String showMenu(Model model) {
    	
    	// ニュースAPIからデータを取得
        NewsApiForm newsData = menuService.getNewsApi();
        
        // モデルにニュースデータを追加
        model.addAttribute("newsData", newsData);  
        // 表示するテンプレート名を指定
        model.addAttribute("templateName", "news");
        
        return "layout";
    }
}