package com.example.guru.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.guru.form.LoginForm;

/**
 * ログイン画面を表示するためのコントローラークラス。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-19
 */
@Controller
public class LoginController extends BaseController {

    /**
     * ログインページを表示します。セッションから前回のログイン試行情報を取得し、モデルに追加します。
     * 
     * @param model ビューに渡すモデル
     * @param session 現在のセッション
     * @return ログイン画面のビュー名
     */
    @GetMapping("/login")
    public String login(Model model, HttpSession session) {
    	
    	// セッションからログイン試行情報を取得
        LoginForm loginForm = (LoginForm) session.getAttribute("loginForm"); 
        
        // デフォルトの新しいインスタンスを使用
        if (loginForm == null) {
            loginForm = new LoginForm();
        }
        
        // モデルにログイン情報を追加
        model.addAttribute("loginForm", loginForm);
        
        return "login";
    }
}