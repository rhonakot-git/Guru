package com.example.guru.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import jakarta.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.guru.dto.UserReportsDto;
import com.example.guru.repository.MUserRepository;

/**
 * ユーザーEXCELの表示と生成を管理するコントローラークラス。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-31
 */
@Controller
@RequestMapping("/user_excel")
public class UserExcelController {

	@Autowired
    private MUserRepository mUserRepository; // ユーザー情報を取得するリポジトリ

    @Autowired
    private ResourceLoader resourceLoader; // テンプレートを読み込むためのツール
    
    /**
     * ユーザーEXCEL出力を表示します。
     * 
     * @param model ビューに渡すモデル
     * @return レイアウトテンプレートのビュー名
     */
    @GetMapping
    public String showReportPage(Model model) {
        // 表示するテンプレート名をモデルに追加
        model.addAttribute("templateName", "user_excel");
        
        // Thymeleafなどのテンプレートエンジンを使用してレイアウトを描画
        return "layout";
    }

    /**
     * ユーザー情報を基にEXCELを生成し、レスポンスとして出力します。
     * 
     * @param response HTTPレスポンスオブジェクト
     * @throws Exception レポート生成処理中にエラーが発生した場合
     */
    @PostMapping("/excel")
	public void generateExcelReport(HttpServletResponse response) throws Exception {
	        
        // ログインユーザーを取得
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        
        // 現在の日時を取得
        LocalDateTime now = LocalDateTime.now();
        
        // 日時フォーマッターを作成（"yyyyMMddHHmmssSSS"形式）
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        
        // フォーマットされた日時を取得
        String formattedDate = now.format(formatter);
        
        // ファイル名を生成
        String fileName = "ユーザーExcel出力_" + formattedDate + ".xlsx";

        // ファイル名をUTF-8でURLエンコード
        String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replace("+", "%20");
	        
        // excelテンプレートを読み込む
	    Resource resource = resourceLoader.getResource("classpath:templates/ユーザーExcel出力.xlsx");
	    InputStream inputStream = resource.getInputStream();
	
	    // テンプレートをExcelファイルとして扱う
	    Workbook workbook = new XSSFWorkbook(inputStream);
	    // Excelの1番目のシートを使う
	    Sheet sheet = workbook.getSheetAt(0);
	
	        // 出力日⇒現在日付
	        Row row6 = sheet.getRow(5); // Excelの6行目
	        if (row6 == null) {
	            // 6行目が存在しない場合、新しく作る
	            row6 = sheet.createRow(5);
	        }
	        // E列
	        Cell dateCell = row6.getCell(4);
	        if (dateCell == null) {
	            // もしE6セルが存在しない場合、新しく作る
	            dateCell = row6.createCell(4);
	        }
	        
	        // 現在の日時
	        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
	        dateCell.setCellValue(currentDate);
	
	        // 出力日⇒現在日付
	        Row row7 = sheet.getRow(6); // Excelの6行目
	        if (row7 == null) {
	            // 7行目が存在しない場合、新しく作る
	            row7 = sheet.createRow(6);
	        }
	        // E列
	        Cell userCell = row7.getCell(4);
	        if (userCell == null) {
	            // もしE6セルが存在しない場合、新しく作る
	        	userCell = row7.createCell(4);
	        }
	        
	        // 出力者
	        userCell.setCellValue(currentUser);
	
	        // スタイルの定義
	        CellStyle style = workbook.createCellStyle();
	        style.setBorderTop(BorderStyle.THIN);    // 上罫線
	        style.setBorderBottom(BorderStyle.THIN); // 下罫線
	        style.setBorderLeft(BorderStyle.THIN);   // 左罫線
	        style.setBorderRight(BorderStyle.THIN);  // 右罫線
	        
	        // データベースから全ユーザー情報を取得
	        List<UserReportsDto> userList = mUserRepository.findAllUser();

	        // ユーザー情報を書き込むループ
	        int rowNum = 10; // データ出力開始行（11行目）
	        for (UserReportsDto user : userList) {
	            Row row = sheet.getRow(rowNum);
	            if (row == null) {
	                row = sheet.createRow(rowNum); // 行が存在しない場合は作成
	            }

	            // 各列にデータを入れる
	            for (int col = 0; col < 7; col++) {
	                Cell cell = row.createCell(col);
	                cell.setCellStyle(style); // 罫線スタイルを適用
	                switch (col) {
	                    case 0: cell.setCellValue(user.getUserId()); break;      // ユーザーID
	                    case 1: cell.setCellValue(user.getUserName()); break;    // ユーザー名
	                    case 2: cell.setCellValue(user.getRole()); break;        // ロール
	                    case 3: cell.setCellValue(user.getGender()); break;      // 性別
	                    case 4: cell.setCellValue(user.getEmail()); break;       // メールアドレス
	                    case 5: cell.setCellValue(user.getPostalCode()); break;  // 郵便番号
	                    case 6: cell.setCellValue(user.getAddress()); break;     // 住所
	                }
	            }
	            rowNum++; // 次の行に進む
	        }
	
	        // ブラウザでExcelファイルをダウンロードする設定
	        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	        // ダウンロード時のファイル名を設定
	        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFileName);
	
	        // **Excelファイルをブラウザに送信**
	        try (OutputStream out = response.getOutputStream()) {
	            // 作成したExcelデータをブラウザに送る
	            workbook.write(out);
	        } finally {
	            // 使用したリソースを閉じる（メモリ解放）
	            workbook.close();
	            inputStream.close();
	        }
	    }
}