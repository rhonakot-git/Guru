package com.example.guru.dto;

/**
 * ユーザー検索結果の詳細情報を保持するDTOクラス。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-19
 */
public class UserSearchDetail {

	/** ユーザーID */
    private String userId;
    
    /** ユーザー名 */
    private String userName;
    
    /** ロールID(コード) */
    private String roleId;
    
    /** ロール(名前) */
    private String roleName;
    
    /** 性別(コード) */
    private String gender;

    /** 性別(名前) */
    private String genderName;
    
    /** メールアドレス */
    private String email;
    
    /** 郵便番号 */
    private String postalCode;
    
    /** 住所1 */
    private String address1;
    
    /** 住所2 */
    private String address2;
    
    /** 備考 */
    private String remarks;

	public UserSearchDetail(String userId, String userName, String roleId, String roleName, String gender,
			String genderName, String email, String postalCode, String address1, String address2, String remarks) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.roleId = roleId;
		this.roleName = roleName;
		this.gender = gender;
		this.genderName = genderName;
		this.email = email;
		this.postalCode = postalCode;
		this.address1 = address1;
		this.address2 = address2;
		this.remarks = remarks;
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
	 * roleId を取得します。
	 *
	 * @return roleId
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * roleIdを設定します。
	 *
	 * @param roleId
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * roleName を取得します。
	 *
	 * @return roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * roleNameを設定します。
	 *
	 * @param roleName
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
	 * genderName を取得します。
	 *
	 * @return genderName
	 */
	public String getGenderName() {
		return genderName;
	}

	/**
	 * genderNameを設定します。
	 *
	 * @param genderName
	 */
	public void setGenderName(String genderName) {
		this.genderName = genderName;
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
	 * address1 を取得します。
	 *
	 * @return address1
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * address1を設定します。
	 *
	 * @param address1
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	 * address2 を取得します。
	 *
	 * @return address2
	 */
	public String getAddress2() {
		return address2;
	}

	/**
	 * address2を設定します。
	 *
	 * @param address2
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**
	 * remarks を取得します。
	 *
	 * @return remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * remarksを設定します。
	 *
	 * @param remarks
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}