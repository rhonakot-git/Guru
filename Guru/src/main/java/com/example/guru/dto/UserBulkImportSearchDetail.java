package com.example.guru.dto;

/**
 * ユーザー一括インポート検索結果の詳細情報を保持するDTOクラス。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-24
 */
public class UserBulkImportSearchDetail {

	/** 取込番号 */
    private String importId;
    
    /** ステータス */
    private String status;  
    
    /** アップロードファイル名 */
    private String fileName;
    
    /** エラーファイルパス */
    private String errorFilePath; 
    
    /** 取込日時 */
    private String importDateTime; 

    /**
     * コンストラクタ
     */
    public UserBulkImportSearchDetail(String importId, String status, String fileName, String errorFilePath, String importDateTime) {
        this.importId = importId;
        this.status = status;
        this.fileName = fileName;
        this.errorFilePath = errorFilePath;
        this.importDateTime = importDateTime;
    }

	/**
	 * importId を取得します。
	 *
	 * @return importId
	 */
	public String getImportId() {
		return importId;
	}

	/**
	 * importIdを設定します。
	 *
	 * @param importId
	 */
	public void setImportId(String importId) {
		this.importId = importId;
	}

	/**
	 * status を取得します。
	 *
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * statusを設定します。
	 *
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * fileName を取得します。
	 *
	 * @return fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * fileNameを設定します。
	 *
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * errorFilePath を取得します。
	 *
	 * @return errorFilePath
	 */
	public String getErrorFilePath() {
		return errorFilePath;
	}

	/**
	 * errorFilePathを設定します。
	 *
	 * @param errorFilePath
	 */
	public void setErrorFilePath(String errorFilePath) {
		this.errorFilePath = errorFilePath;
	}

	/**
	 * importDateTime を取得します。
	 *
	 * @return importDateTime
	 */
	public String getImportDateTime() {
		return importDateTime;
	}

	/**
	 * importDateTimeを設定します。
	 *
	 * @param importDateTime
	 */
	public void setImportDateTime(String importDateTime) {
		this.importDateTime = importDateTime;
	}
}