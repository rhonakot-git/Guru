package com.example.guru.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "IMPORT_HISTORY", schema = "KOTA")
public class ImportHistory {
    @Id
    @Column(name = "IMPORT_ID")
    private Long importId;

    @Column(name = "IMPORT_TYPE")
    private String importType;  // インポートの種類

    @Column(name = "STATUS")
    private String status;      // ステータス

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "ERROR_FILE_PATH")
    private String errorFilePath;

    @Column(name = "IMPORT_DATE_TIME")
    private Timestamp importDateTime;  // 取込日時（追加）

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "UPDATE_USER")
    private String updateUser;

    @Column(name = "CREATE_DATE_TIME")
    private Timestamp createDateTime;

    @Column(name = "UPDATE_DATE_TIME")
    private Timestamp updateDateTime;

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
	 * importType を取得します。
	 *
	 * @return importType
	 */
	public String getImportType() {
		return importType;
	}

	/**
	 * importTypeを設定します。
	 *
	 * @param importType
	 */
	public void setImportType(String importType) {
		this.importType = importType;
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
	public Timestamp getImportDateTime() {
		return importDateTime;
	}

	/**
	 * importDateTimeを設定します。
	 *
	 * @param importDateTime
	 */
	public void setImportDateTime(Timestamp importDateTime) {
		this.importDateTime = importDateTime;
	}

	/**
	 * createUser を取得します。
	 *
	 * @return createUser
	 */
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * createUserを設定します。
	 *
	 * @param createUser
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * updateUser を取得します。
	 *
	 * @return updateUser
	 */
	public String getUpdateUser() {
		return updateUser;
	}

	/**
	 * updateUserを設定します。
	 *
	 * @param updateUser
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	/**
	 * createDateTime を取得します。
	 *
	 * @return createDateTime
	 */
	public Timestamp getCreateDateTime() {
		return createDateTime;
	}

	/**
	 * createDateTimeを設定します。
	 *
	 * @param createDateTime
	 */
	public void setCreateDateTime(Timestamp createDateTime) {
		this.createDateTime = createDateTime;
	}

	/**
	 * updateDateTime を取得します。
	 *
	 * @return updateDateTime
	 */
	public Timestamp getUpdateDateTime() {
		return updateDateTime;
	}

	/**
	 * updateDateTimeを設定します。
	 *
	 * @param updateDateTime
	 */
	public void setUpdateDateTime(Timestamp updateDateTime) {
		this.updateDateTime = updateDateTime;
	}
}