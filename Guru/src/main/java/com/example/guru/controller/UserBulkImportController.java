package com.example.guru.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.guru.dto.UserBulkImportSearchDetail;
import com.example.guru.entity.ImportHistory;
import com.example.guru.form.UserBulkImportForm;
import com.example.guru.form.UserBulkImportSearchForm;
import com.example.guru.repository.ImportHistoryRepository;
import com.example.guru.service.GenericService;
import com.example.guru.service.UserBulkImportService;

/**
 * ユーザー一括インポートを管理するコントローラークラス。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-24
 */
@Controller
@RequestMapping("/user_bulk_import")
public class UserBulkImportController {
	
	@Autowired
    private ImportHistoryRepository importHistoryRepository;

    @Autowired
    private UserBulkImportService userBulkImportService;
    
    @Autowired
    private GenericService genericService;
    
    // ページの表示件数
    @Value("${user-bulk-import.search.page-size}")
    private int pageSize;
    
    /**
     * ユーザー一括インポートページを表示します。
     * 
     * @param model ビューに渡すモデル
     * @return レイアウトテンプレートのビュー名
     */
    @GetMapping
    public String showImportPage(Model model) {
    	
        // インポートステータスを追加
        model.addAttribute("status", genericService.getCategoryMasterData("IMPORT_STATUS"));
        
        // フォームの初期化
        model.addAttribute("userForm", new UserBulkImportForm());
        
        // 検索フォームの初期化
        model.addAttribute("searchForm", new UserBulkImportSearchForm());
        
        // コンテンツテンプレートを指定
        model.addAttribute("templateName", "user_bulk_import");
         
        return "layout";
    }

    /**
     * ユーザー一括インポートページを表示します。
     * 
     * @param model ビューに渡すモデル
     * @return レイアウトテンプレートのビュー名
     */
    @PostMapping("/import")
    public String uploadFile(
    		@ModelAttribute("userForm") UserBulkImportForm form,
            BindingResult bindingResult,
            Model model) {
    	
    	// 検索フォームの初期化
        model.addAttribute("searchForm", new UserBulkImportSearchForm());
    	
    	// インポートステータスを追加
        model.addAttribute("status", genericService.getCategoryMasterData("IMPORT_STATUS"));
        
        // コンテンツテンプレートを指定
        model.addAttribute("templateName", "user_bulk_import");
        
        // 一時ファイルに保存
        String tempFilePath = System.getProperty("java.io.tmpdir") + "/" + form.getFile().getOriginalFilename();
        try {
        	form.getFile().transferTo(new File(tempFilePath)); // ファイルを一時ディレクトリに保存
        } catch (IOException e) {
        }

    	// 履歴情報の登録
        ImportHistory history = userBulkImportService.startImport(form.getFile(), bindingResult);
        
        if (bindingResult.hasErrors()) {
            return "layout";
        }
        
        // 一括インポート処理(非同期)
        userBulkImportService.processImportAsync(history.getImportId(), tempFilePath, SecurityContextHolder.getContext().getAuthentication().getName());
        
        UserBulkImportSearchForm searchForm = new UserBulkImportSearchForm();
        searchForm.setImportId(history.getImportId());
  
        // インポート履歴詳細情報を取得
        Page<UserBulkImportSearchDetail> importHistoryPage = userBulkImportService.getImportHistoryPage(searchForm, 1, pageSize);
        
        // 検索結果をモデルに追加
        model.addAttribute("importHistoryPage", importHistoryPage);
        
        // 検索フォームの初期化
        model.addAttribute("searchForm", new UserBulkImportSearchForm());
        
        return "layout";
    }
    
    /**
     * CSVファイルをアップロードし、インポート処理を開始します。
     * 
     * @param form アップロードされたファイルを含むフォーム
     * @param bindingResult バリデーション結果
     * @param model ビューに渡すモデル
     * @return レイアウトテンプレートのビュー名
     */
    @PostMapping("/search")
    public String search(
    		@Valid @ModelAttribute("searchForm") UserBulkImportSearchForm form,
            BindingResult bindingResult,
            @RequestParam(value = "page", defaultValue = "1") int page,
            Model model) {
    	
    	// フォームの初期化
        model.addAttribute("userForm", new UserBulkImportForm());
    	
    	// インポートステータスを追加
        model.addAttribute("status", genericService.getCategoryMasterData("IMPORT_STATUS"));
        
        // コンテンツテンプレートを指定
        model.addAttribute("templateName", "user_bulk_import");
        
        if (bindingResult.hasErrors()) {
            return "layout";
        }
        
        // インポート履歴詳細情報を取得
        Page<UserBulkImportSearchDetail> importHistoryPage = userBulkImportService.getImportHistoryPage(form, page, pageSize);
        
        // 検索結果をモデルに追加
        model.addAttribute("importHistoryPage", importHistoryPage);
    	
    	return "layout";
    }
    
    /**
     * 指定されたインポートIDのエラーCSVファイルをダウンロードします。
     * 
     * @param importId インポートID
     * @return ダウンロード用のResponseEntity
     */
    @GetMapping("/import/csv/{importId}")
    public ResponseEntity<Resource> downloadCsv(@PathVariable Long importId) {
    	
        // importIdから履歴を取得
        ImportHistory history = importHistoryRepository.findById(importId)
                .orElseThrow(() -> new RuntimeException("Import history not found"));

        // エラーファイルパスがnullの場合は404を返す
        String filePath = history.getErrorFilePath();
        if (filePath == null) {
            return ResponseEntity.notFound().build();
        }

        // ファイルを取得
        Path path = Paths.get(filePath);
        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

        // ダウンロード用のレスポンスを作成
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}