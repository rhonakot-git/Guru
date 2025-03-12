package com.example.guru.config; // パッケージ名

import jakarta.servlet.http.HttpSession; // HttpSession用インポート

import org.springframework.beans.factory.annotation.Autowired; // Autowired用インポート
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean; // Bean用インポート
import org.springframework.context.annotation.Configuration; // 設定クラス用インポート
import org.springframework.security.config.annotation.web.builders.HttpSecurity; // HttpSecurity用インポート
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; // Spring Security有効化用インポート
import org.springframework.security.core.userdetails.UserDetailsService; // UserDetailsService用インポート
import org.springframework.security.web.SecurityFilterChain; // SecurityFilterChain用インポート
import org.springframework.security.web.authentication.AuthenticationFailureHandler; // AuthenticationFailureHandler用インポート

import com.example.guru.form.LoginForm; // LoginForm用インポート
import com.example.guru.service.UserService; // UserService用インポート

/**
 * Spring Security設定クラス
 * @author kota
 * @since 2023-10-01
 */
@Configuration // 設定クラスであることを示す
@EnableWebSecurity // Spring Securityを有効化
public class SecurityConfig {

    @Autowired // UserServiceを注入
    private UserService userService;
    
    @Autowired // MessageSourceを注入
    private MessageSource messageSource;

    @Bean // SecurityFilterChainをBeanとして定義
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http // HttpSecurityを設定開始
        .csrf(csrf -> csrf // CSRF を全面的に有効化
                .ignoringRequestMatchers("/login") // ログインは除外（必要に応じて）
            )
            .authorizeHttpRequests(authz -> authz // リクエスト認可を設定
                .requestMatchers("/login").permitAll() // /loginは認証不要
                .requestMatchers("/css/**").permitAll() // 静的リソースを許可
                .anyRequest().authenticated() // それ以外は認証必要
            )
            .formLogin(form -> form // フォームログインを設定
                .loginPage("/login") // ログインページを指定
                .usernameParameter("userId") // ユーザー名パラメータを"userId"に設定
                .passwordParameter("password") // パスワードパラメータを"password"に設定
                .defaultSuccessUrl("/menu", true) // ログイン成功時のリダイレクト先
                .failureHandler(authenticationFailureHandler()) // 認証失敗ハンドラを設定
                .permitAll() // ログインページは誰でもアクセス可能
            )
            .logout(logout -> logout // ログアウトを設定
                .logoutSuccessUrl("/login") // ログアウト成功時のリダイレクト先
                .permitAll() // ログアウトは誰でも可能
            )
            .sessionManagement(session -> session // セッション管理を設定
                .invalidSessionUrl("/login") // 無効なセッション時のリダイレクト先
                .maximumSessions(1) // 同一ユーザーの最大セッション数を1に制限
                .expiredUrl("/login") // セッション期限切れ時のリダイレクト先
            )
            .userDetailsService(userDetailsService()); // カスタムUserDetailsServiceを設定
        return http.build(); // SecurityFilterChainを構築して返す
    }

    @Bean // UserDetailsServiceをBeanとして定義
    public UserDetailsService userDetailsService() throws Exception{
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
                .build();
        };
    }

    @Bean // 認証失敗ハンドラをBeanとして定義
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            HttpSession session = request.getSession();
            // 入力値をLoginFormに設定
            LoginForm loginForm = new LoginForm();
            loginForm.setUserId(request.getParameter("userId")); // userIdを保持
            session.setAttribute("loginForm", loginForm); // セッションに保存

            // 例外メッセージをセッションに保存
            session.setAttribute("errorMessage", messageSource.getMessage("user.invalid",null, null, null)); // 具体的なエラーメッセージを保存
            session.setAttribute("SPRING_SECURITY_LAST_EXCEPTION", exception); // 例外オブジェクトも保存（デバッグ用）

            response.sendRedirect("/login?error"); // エラー付きでリダイレクト
        };
    }
}