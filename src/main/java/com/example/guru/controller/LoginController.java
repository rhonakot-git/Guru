package com.example.guru.controller; // パッケージ名

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller; // コントローラー用インポート
import org.springframework.ui.Model; // Model用インポート
import org.springframework.web.bind.annotation.GetMapping; // GETマッピング用インポート

import com.example.guru.form.LoginForm; // LoginForm用インポート

/**
 * ログイン画面コントローラー
 * @author kota
 * @since 2023-10-01
 */
@Controller // コントローラークラスであることを示す
public class LoginController {

    @GetMapping("/login") // /loginへのGETリクエストを処理
    public String login(Model model, HttpSession session) {
        // セッションから前回のloginFormを取得（認証失敗時）
        LoginForm loginForm = (LoginForm) session.getAttribute("loginForm");
        if (loginForm == null) {
            loginForm = new LoginForm(); // デフォルトの新しいインスタンス
        }
        model.addAttribute("loginForm", loginForm); // Modelに追加
        return "login"; // login.htmlを返す
    }
}