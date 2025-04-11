package com.example.guru.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.guru.dto.UserSearchDetail;
import com.example.guru.entity.MUser;
import com.example.guru.form.UserForm;
import com.example.guru.form.UserSearchForm;
import com.example.guru.service.GenericService;
import com.example.guru.service.UserService;

/**
 * ユーザー関連の操作（検索、登録、更新）を管理するコントローラークラス。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-19
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private GenericService genericService;

    // ページの表示件数
    @Value("${user.search.page-size}")
    private int pageSize;

    /**
     * 現在のセッションを取得します。
     * 
     * @return 現在のHttpSession
     */
    private HttpSession getSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true);  // セッションを取得（存在しない場合は新規作成）
    }

    /**
     * ユーザー検索フォームを表示します。
     * 
     * @param model ビューに渡すモデル
     * @return レイアウトテンプレートのビュー名
     */
    @GetMapping("/search")
    public String showSearchForm(Model model) {
    	
    	// 検索フォームをモデルに追加
        model.addAttribute("searchForm", new UserSearchForm());
        // ロールデータを追加
        model.addAttribute("roles", genericService.getCategoryMasterData("ROLE"));
        // 性別データを追加
        model.addAttribute("genders", genericService.getCategoryMasterData("GENDER"));
        // 表示するテンプレート名を指定
        model.addAttribute("templateName", "user_search");
        
        return "layout";
    }

    /**
     * ユーザー検索を実行し、結果を表示します。
     * 
     * @param searchForm 検索条件を含むフォーム
     * @param page ページ番号（デフォルトは1）
     * @param model ビューに渡すモデル
     * @return レイアウトテンプレートのビュー名
     */
    @PostMapping("/search")
    public String searchUsers(UserSearchForm searchForm,
                             @RequestParam(value = "page", defaultValue = "1") int page,
                             Model model) {
    	
    	// ロールデータを追加
        model.addAttribute("roles", genericService.getCategoryMasterData("ROLE"));
        // 性別データを追加
        model.addAttribute("genders", genericService.getCategoryMasterData("GENDER"));
        
        // 検索結果を取得
        Page<UserSearchDetail> userPage = userService.getUserDetailPage(searchForm, page, pageSize);
        
        // 検索フォームをモデルに追加
        model.addAttribute("searchForm", searchForm);
        // 検索結果をモデルに追加
        model.addAttribute("userPage", userPage);
        // 表示するテンプレート名を指定
        model.addAttribute("templateName", "user_search");
        
        return "layout";
    }
    
    /**
     * ユーザー検索を実行し、結果をCSV出力します。
     * 
     * @param searchForm 検索条件を含むフォーム
     * @param page ページ番号（デフォルトは1）
     * @param model ビューに渡すモデル
     * @return レイアウトテンプレートのビュー名
     */
    @PostMapping("/csv")
    public void exportCsv(UserSearchForm searchForm, HttpServletResponse response) throws IOException {
        // ユーザー情報を取得
        List<UserSearchDetail> users = userService.getAllUserDetails(searchForm);

        // 現在の日時を取得し、ファイル名を生成
        String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssSSS"));
        String fileName = currentDateTime + "_user_detail.csv";

        // レスポンスの設定
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.setCharacterEncoding("UTF-8");

        // CSVデータを生成
        try (CSVPrinter csvPrinter = new CSVPrinter(response.getWriter(), CSVFormat.DEFAULT.withHeader(
                "ユーザーID", "ユーザー名", "パスワード", "パスワード(確認)", "ロールID", "ロール名", "性別ID", "性別", "メールアドレス", "郵便番号", "住所1", "住所2", "備考"))) {
            for (UserSearchDetail user : users) {
                csvPrinter.printRecord(
                        user.getUserId(),
                        user.getUserName(),
                        "",
                        "",
                        user.getRoleId(),
                        user.getRoleName(),
                        user.getGender(),
                        user.getGenderName(),
                        user.getEmail(),
                        user.getPostalCode(),
                        user.getAddress1(),
                        user.getAddress2(),
                        user.getRemarks()
                );
            }
        }
    }

    /**
     * ユーザー登録フォームを表示します。
     * 
     * @param model ビューに渡すモデル
     * @return レイアウトテンプレートのビュー名
     */
    @GetMapping("/userRegist")
    public String showRegisterForm(Model model) {
    	
    	// セッション取得
        HttpSession session = getSession();
    	
    	// セッションから"userSearchForm"を削除
        session.removeAttribute("searchForm");
        // セッションから"currentPage"を削除
        session.removeAttribute("currentPage");
    	
    	// 登録フォームをモデルに追加
        model.addAttribute("userForm", new UserForm());
        // 登録処理のURLを指定
        model.addAttribute("actionUrl", "/user/userRegist");
        // ロールデータを追加
        model.addAttribute("roles", genericService.getCategoryMasterData("ROLE"));
        // 性別データを追加
        model.addAttribute("genders", genericService.getCategoryMasterData("GENDER"));
        // 表示するテンプレート名を指定
        model.addAttribute("templateName", "user_form");
        
        return "layout";
    }

    /**
     * ユーザー更新フォームを表示します。
     * 
     * @param userId ユーザーID（パス変数）
     * @param message メッセージ（任意）
     * @param model ビューに渡すモデル
     * @return レイアウトテンプレートのビュー名
     */
    @GetMapping("/update/{userId}")
    public String showUpdateForm(
            @PathVariable String userId,
            @RequestParam(value = "message", required = false) String message,
            Model model) {
    	
    	// セッション取得
        HttpSession session = getSession();
        
        // セッションからユーザー検索フォーム取得
        UserSearchForm searchForm = (UserSearchForm) session.getAttribute("searchForm");
        // セッションからカレントページ取得
        Integer currentPage = (Integer) session.getAttribute("currentPage");
        
        // 戻るボタン表示
        boolean showBackButton = searchForm != null && currentPage != null;

        // ユーザーIDからユーザー情報を取得
        MUser user = userService.findByUserId(userId);
        if (user == null) {
            model.addAttribute("error", "ユーザーが見つかりません");
            return "error_page";  // エラーページを表示
        }

        // ユーザーフォーム
        UserForm userForm = new UserForm();
        userForm.setUserId(user.getUserId());				// ユーザーID
        userForm.setUserName(user.getUserName());			// ユーザー名
        userForm.setRoleId(user.getRoleId());				// ロールID
        userForm.setEmail(user.getEmail());					// メールアドレス
        userForm.setPostalCode(user.getPostalCode());		// 郵便番号
        userForm.setPassword(user.getPassword());			// パスワード
        userForm.setConfirmPassword(user.getPassword());	// パスワード（確認）
        userForm.setAddress1(user.getAddress1());			// 住所1
        userForm.setAddress2(user.getAddress2());			// 住所2
        userForm.setGender(user.getGender());				// 性別
        userForm.setRemarks(user.getRemarks());				// 備考
        
        // ユーザー情報をモデルに追加
        model.addAttribute("userForm", userForm);
        // 検索フォームをモデルに追加
        model.addAttribute("searchForm", searchForm);
        // 現在のページをモデルに追加
        model.addAttribute("currentPage", currentPage);
        // 戻るボタンの表示を制御
        model.addAttribute("showBackButton", showBackButton);
        // 更新処理のURLを指定
        model.addAttribute("actionUrl", "/user/update/process");
        // ロールデータを追加
        model.addAttribute("roles", genericService.getCategoryMasterData("ROLE"));  
        // 性別データを追加
        model.addAttribute("genders", genericService.getCategoryMasterData("GENDER"));
        
        if (message != null) {
        	// メッセージをモデルに追加
            model.addAttribute("message", message);
        }
        // 表示するテンプレート名を指定
        model.addAttribute("templateName", "user_form");
        
        return "layout";
    }

    /**
     * ユーザー検索結果からユーザー更新フォームを表示します。
     * 
     * @param selectedUserId 選択されたユーザーID
     * @param searchForm 検索フォーム
     * @param page ページ番号
     * @param model ビューに渡すモデル
     * @return レイアウトテンプレートのビュー名
     */
    @PostMapping("/update/show")
    public String showUpdateFormFromPost(
            String selectedUserId,
            UserSearchForm searchForm,
            @RequestParam(value = "page", defaultValue = "1") int page,
            Model model) {
    	
    	// セッション取得
        HttpSession session = getSession();
        
        // セッションに検索フォームを保存
        session.setAttribute("searchForm", searchForm);
        // セッションに現在のページを保存
        session.setAttribute("currentPage", page);

        // ユーザーIDからユーザー情報を取得
        MUser user = userService.findByUserId(selectedUserId);
        if (user == null) {
            model.addAttribute("error", "ユーザーが見つかりません");
            return "error_page";  // エラーページを表示
        }

        // ユーザーフォーム
        UserForm userForm = new UserForm();
        userForm.setUserId(user.getUserId());				// ユーザーID
        userForm.setUserName(user.getUserName());			// ユーザー名
        userForm.setRoleId(user.getRoleId());				// ロールID
        userForm.setEmail(user.getEmail());					// メールアドレス
        userForm.setPostalCode(user.getPostalCode());		// 郵便番号
        userForm.setPassword(user.getPassword());			// パスワード
        userForm.setConfirmPassword(user.getPassword());	// パスワード（確認）
        userForm.setAddress1(user.getAddress1());			// 住所1
        userForm.setAddress2(user.getAddress2());			// 住所2
        userForm.setGender(user.getGender());				// 性別
        userForm.setRemarks(user.getRemarks());				// 備考

        // ユーザー情報をモデルに追加
        model.addAttribute("userForm", userForm);
        // 検索フォームをモデルに追加
        model.addAttribute("searchForm", searchForm);
        // 現在のページをモデルに追加
        model.addAttribute("currentPage", page);  
        // 戻るボタンを表示
        model.addAttribute("showBackButton", true);
        // 更新処理のURLを指定
        model.addAttribute("actionUrl", "/user/update/process");
        // ロールデータを追加
        model.addAttribute("roles", genericService.getCategoryMasterData("ROLE"));
        // 性別データを追加
        model.addAttribute("genders", genericService.getCategoryMasterData("GENDER"));
        // 表示するテンプレート名を指定
        model.addAttribute("templateName", "user_form");
        
        return "layout";
    }

    /**
     * ユーザー情報を更新します。
     * 
     * @param userForm ユーザー情報フォーム
     * @param bindingResult バリデーション結果
     * @param model ビューに渡すモデル
     * @return リダイレクト先URL
     */
    @PostMapping("/update/process")
    public String updateUser(@Valid @ModelAttribute("userForm") UserForm userForm, 
                             BindingResult bindingResult, Model model) {
    	
    	// ユーザー情報を更新
        userService.updateUser(userForm, bindingResult);
        
        // エラーがある場合
        if (bindingResult.hasErrors()) {
        	// セッション取得
            HttpSession session = getSession();
            
            // セッションからユーザー検索フォーム取得
            UserSearchForm searchForm = (UserSearchForm) session.getAttribute("searchForm");
            // セッションからカレントページ取得
            Integer currentPage = (Integer) session.getAttribute("currentPage");

            // 検索フォームをモデルに追加
            model.addAttribute("searchForm", searchForm);
            // 現在のページをモデルに追加
            model.addAttribute("currentPage", currentPage);
            // 戻るボタンの表示を制御
            model.addAttribute("showBackButton", searchForm != null && currentPage != null);
            // ロールデータを追加
            model.addAttribute("roles", genericService.getCategoryMasterData("ROLE"));
            // 性別データを追加
            model.addAttribute("genders", genericService.getCategoryMasterData("GENDER"));
            // 更新処理のURLを指定
            model.addAttribute("actionUrl", "/user/update/process");
            // 表示するテンプレート名を指定
            model.addAttribute("templateName", "user_form");
            
            return "layout";
        }
        
        // メッセージをエンコード
        String encodedMessage = URLEncoder.encode("ユーザー情報を更新しました。", StandardCharsets.UTF_8);
        
        // 更新完了メッセージ付きでリダイレクト
        return "redirect:/user/update/" + userForm.getUserId() + "?message=" + encodedMessage;
    }

    /**
     * 新しいユーザーを登録します。
     * 
     * @param userForm ユーザー情報フォーム
     * @param bindingResult バリデーション結果
     * @param model ビューに渡すモデル
     * @return リダイレクト先URL
     */
    @PostMapping("/userRegist")
    public String registerUser(@Valid @ModelAttribute("userForm") UserForm userForm, 
                               BindingResult bindingResult, Model model) {
    	
    	// エラーがある場合
        if (bindingResult.hasErrors()) {
        	// ロールデータを追加
            model.addAttribute("roles", genericService.getCategoryMasterData("ROLE"));
            // 性別データを追加
            model.addAttribute("genders", genericService.getCategoryMasterData("GENDER"));
            // 登録処理のURLを指定
            model.addAttribute("actionUrl", "/user/userRegist");
            // コンテンツテンプレートを指定
            model.addAttribute("contentTemplate", "user_form :: content");
            
            return "layout";
        }

        // ユーザー情報を登録
        userService.registerUser(userForm, bindingResult);
        
        if (bindingResult.hasErrors()) {
        	// ロールデータを追加
            model.addAttribute("roles", genericService.getCategoryMasterData("ROLE"));
            // 性別データを追加
            model.addAttribute("genders", genericService.getCategoryMasterData("GENDER"));
            // 登録処理のURLを指定
            model.addAttribute("actionUrl", "/user/userRegist");
            // コンテンツテンプレートを指定
            model.addAttribute("contentTemplate", "user_form :: content");
            
            return "layout";
        }
        
        // メッセージをエンコード
        String encodedMessage = URLEncoder.encode("ユーザー情報を登録しました。", StandardCharsets.UTF_8);
        
        // 登録完了メッセージ付きでリダイレクト
        return "redirect:/user/update/" + userForm.getUserId() + "?message=" + encodedMessage;
    }
}