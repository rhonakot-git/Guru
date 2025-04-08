package com.example.guru.form;

import org.springframework.web.multipart.MultipartFile;

public class UserBulkImportForm {
	
	private MultipartFile file;
    // 必要なら他のフィールド（例: importType など）を追加

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}