package com.example.guru.form;

import com.example.guru.validation.ValidYYYYMMDD;

public class UserBulkImportSearchForm {
    private String status;
    
    private Long importId;
    
    @ValidYYYYMMDD(message = "取込日時（FROM）はYYYYMMDD形式で入力してください。")
    private String importDateFrom;

    @ValidYYYYMMDD(message = "取込日時（TO）はYYYYMMDD形式で入力してください。")
    private String importDateTo;

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
	 * importId を取得します。
	 *
	 * @return importId
	 */
	public Long getImportId() {
		return importId;
	}

	/**
	 * importIdを設定します。
	 *
	 * @param importId
	 */
	public void setImportId(Long importId) {
		this.importId = importId;
	}

	/**
	 * importDateFrom を取得します。
	 *
	 * @return importDateFrom
	 */
	public String getImportDateFrom() {
		return importDateFrom;
	}

	/**
	 * importDateFromを設定します。
	 *
	 * @param importDateFrom
	 */
	public void setImportDateFrom(String importDateFrom) {
		this.importDateFrom = importDateFrom;
	}

	/**
	 * importDateTo を取得します。
	 *
	 * @return importDateTo
	 */
	public String getImportDateTo() {
		return importDateTo;
	}

	/**
	 * importDateToを設定します。
	 *
	 * @param importDateTo
	 */
	public void setImportDateTo(String importDateTo) {
		this.importDateTo = importDateTo;
	}
}