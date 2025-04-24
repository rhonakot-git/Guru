package com.example.guru.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    
    /**
     * ログインフォームの入力値を検証し、認証処理に進むためのエンドポイントです。
     *
     * @param loginForm 入力されたログインフォームデータ（ユーザーID・パスワード）
     * @param bindingResult バリデーション結果。エラーがある場合は画面を再表示します。
     * @param model ビューに渡すモデル
     * @param session 現在のセッション。ログインフォーム情報を一時保存します。
     * @return 入力エラーがある場合はログイン画面、それ以外は認証処理へのフォワード
     */
    @PostMapping("/login")
    public String validateLogin(
        @Valid @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult, Model model, HttpSession session) {
    	
    	// エラーがある場合
        if (bindingResult.hasErrors()) {
            return "login";
        }

        // エラーがなければ、loginForm をセッションに保存して /authenticate にリダイレクト
        session.setAttribute("loginForm", loginForm);
        return "forward:/authenticate";
    }
}