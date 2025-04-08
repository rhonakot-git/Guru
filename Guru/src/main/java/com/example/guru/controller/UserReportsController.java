package com.example.guru.controller;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.guru.dto.UserReportsDto;
import com.example.guru.repository.MUserRepository;

import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignStyle;

/**
 * ユーザー関連レポートの表示と生成を管理するコントローラークラス。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-31
 */
@Controller
@RequestMapping("/user_reports")
public class UserReportsController {

    @Autowired
    private MUserRepository mUserRepository;

    /**
     * レポートページを表示します。
     * 
     * @param model ビューに渡すモデル
     * @return レイアウトテンプレートのビュー名
     */
    @GetMapping
    public String showReportPage(Model model) {
        // 表示するテンプレート名をモデルに追加
        model.addAttribute("templateName", "user_reports");
        
        // Thymeleafなどのテンプレートエンジンを使用してレイアウトを描画
        return "layout";
    }

    /**
     * ユーザー情報を基にPDFレポートを生成し、レスポンスとして出力します。
     * 
     * @param response HTTPレスポンスオブジェクト
     * @throws Exception レポート生成処理中にエラーが発生した場合
     */
    @PostMapping("/reports")
    public void generateReport(HttpServletResponse response) throws Exception {
        try {
            // 現在のログインユーザー名を取得
            String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

            // データベースからすべてのユーザー情報を取得
            List<UserReportsDto> userList = mUserRepository.findAllUser();

            // JRXMLファイルをリソースから読み込む
            InputStream reportStream = getClass().getResourceAsStream("/reports/UserReports_A4.jrxml");
            if (reportStream == null) {
                throw new IllegalStateException("JRXMLファイルが見つかりません: /reports/UserReports_A4.jrxml");
            }

            // JRXMLをコンパイルしてレポートオブジェクトを生成
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // DTOリストをデータソースとして設定
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(userList);

            // レポートに渡すパラメータを設定
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("printerName", currentUser);          // ログインユーザー名をレポートに表示
            parameters.put("REPORT_LOCALE", new Locale("ja", "JP")); // 日本語ロケールを設定

            // レポートをデータソースとパラメータで埋める
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // デフォルトスタイルに日本語フォントを設定
            JRStyle defaultStyle = jasperPrint.getDefaultStyle();
            if (defaultStyle == null) {
                defaultStyle = new JRDesignStyle();
                jasperPrint.setDefaultStyle(defaultStyle);
            }
            defaultStyle.setFontName("NotoSansJP");         // フォント名を設定
            defaultStyle.setPdfFontName("NotoSansJP");      // PDF用のフォント名
            defaultStyle.setPdfEncoding("Identity-H");      // PDFエンコーディング（日本語対応）
            defaultStyle.setPdfEmbedded(true);              // フォントを埋め込み

            // レスポンスの設定（PDF形式でインライン表示）
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=user_report.pdf");

            // PDFをレスポンスストリームに出力
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

        } catch (Exception e) {
            // エラー発生時はスタックトレースを出力し、例外を再スロー
            e.printStackTrace();
            throw new RuntimeException("レポート生成に失敗しました", e);
        }
    }
}