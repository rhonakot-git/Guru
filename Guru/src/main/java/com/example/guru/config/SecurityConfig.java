package com.example.guru.config;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.example.guru.form.LoginForm;
import com.example.guru.service.UserService;

/**
 * Spring Security設定クラス。
 * アプリケーション全体のセキュリティ設定を管理し、認証・認可ルールやセッション管理を定義します。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-19
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    /**
     * セキュリティフィルターチェーンを定義します。
     * CSRF保護、認可ルール、フォームログイン、ログアウト、セッション管理を設定します。
     * 
     * @param http HttpSecurityオブジェクト
     * @return 構築されたSecurityFilterChain
     * @throws Exception 設定処理中に発生する可能性のある例外
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/login")  		// ログインエンドポイントはCSRF保護を除外
            )
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/login").permitAll()  	// ログインページは認証なしでアクセス可能
                .requestMatchers("/css/**").permitAll()  	// CSSなどの静的リソースは認証不要
                .anyRequest().authenticated()  				// その他のリクエストは認証必須
            )
            .formLogin(form -> form
                .loginPage("/login")  							 // ログインページのURLを指定
                .usernameParameter("userId") 					 // ログイン時のユーザーIDパラメータ名
                .passwordParameter("password")  				 // ログイン時のパスワードパラメータ名
                .defaultSuccessUrl("/menu", true)  				 // ログイン成功時のデフォルト遷移先
                .failureHandler(authenticationFailureHandler())  // 認証失敗時の処理ハンドラ
                .permitAll()  									 // ログインページへのアクセスを許可
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login")  // ログアウト成功時のリダイレクト先
                .permitAll()  				 // ログアウト処理を誰でも実行可能に
            )
            .sessionManagement(session -> session
                .invalidSessionUrl("/login")  	// 無効なセッション時のリダイレクト先
                .maximumSessions(1)  			// 同一ユーザーにつき最大1セッションを許可
                .expiredUrl("/login")  			// セッション期限切れ時のリダイレクト先
            )
            .userDetailsService(userDetailsService());  // カスタムユーザー認証サービスを設定
        return http.build();  							// 設定を適用したSecurityFilterChainを返す
    }

    /**
     * カスタムのUserDetailsServiceを定義します。
     * ユーザーIDに基づいてユーザー情報を取得し、Spring Securityの認証用オブジェクトに変換します。
     * 
     * @return UserDetailsServiceの実装
     * @throws Exception ユーザー情報取得時に発生する可能性のある例外
     */
    @Bean
    public UserDetailsService userDetailsService() throws Exception {
        return username -> {
            if (username == null || username.trim().isEmpty()) {
                throw new org.springframework.security.core.userdetails.UsernameNotFoundException("ユーザーIDを入力してください");
            }
            com.example.guru.entity.MUser user = userService.findByUserId(username);
            if (user == null) {
                throw new org.springframework.security.core.userdetails.UsernameNotFoundException("ユーザーIDまたはパスワードが正しくありません");
            }
            return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.getPassword())
                .roles(user.getRoleId())
                .build();  // 認証用ユーザーオブジェクトを生成
        };
    }

    /**
     * 認証失敗時のハンドラを定義します。
     * ログイン失敗時にエラーメッセージをセッションに保存し、ログインページへリダイレクトします。
     * 
     * @return AuthenticationFailureHandlerの実装
     */
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            HttpSession session = request.getSession();
            LoginForm loginForm = new LoginForm();
            loginForm.setUserId(request.getParameter("userId"));  // 入力されたユーザーIDを保持
            session.setAttribute("loginForm", loginForm);  // セッションにフォーム情報を保存

            // エラーメッセージと例外をセッションに保存
            session.setAttribute("errorMessage", messageSource.getMessage("user.invalid", null, null, null));

            response.sendRedirect("/login?error");  // エラー付きでログインページへリダイレクト
        };
    }
}