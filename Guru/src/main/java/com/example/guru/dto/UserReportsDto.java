package com.example.guru.dto;

/**
 * ユーザーレポート出力結果の詳細情報を保持するDTOクラス。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-31
 */
public class UserReportsDto {
	
	/** ユーザーID */
    private String userId;
    
    /** ユーザー名 */
    private String userName;
    
    /** ロール */
    private String role;
    
    /** 性別 */
    private String gender;
    
    /** Eメール */
    private String email;
    
    /** 郵便番号 */
    private String postalCode;
    
    /** 住所 */
    private String address;

    /**
     * コンストラクタ
     */
    public UserReportsDto(String userId, String userName, String role, String gender, String email, String postalCode, String address) {
        this.userId = userId;
        this.userName = userName;
        this.role = role;
        this.gender = gender;
        this.email = email;
        this.postalCode = postalCode;
        this.address = address;
    }

	/**
	 * userId を取得します。
	 *
	 * @return userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * userIdを設定します。
	 *
	 * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * userName を取得します。
	 *
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * userNameを設定します。
	 *
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * role を取得します。
	 *
	 * @return role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * roleを設定します。
	 *
	 * @param role
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * gender を取得します。
	 *
	 * @return gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * genderを設定します。
	 *
	 * @param gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * email を取得します。
	 *
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * emailを設定します。
	 *
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * postalCode を取得します。
	 *
	 * @return postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * postalCodeを設定します。
	 *
	 * @param postalCode
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * address を取得します。
	 *
	 * @return address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * addressを設定します。
	 *
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
}
