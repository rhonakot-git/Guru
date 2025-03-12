package com.example.guru.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "M_MENU")
public class MMenu {
    @Id
    @Column(name = "MENU_ID")
    private String menuId;

    @Column(name = "CATEGORY_ID")
    private String categoryId; // 外部キーではなく単なるStringとして扱う

    @Column(name = "MENU_NAME")
    private String menuName;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "UPDATE_USER")
    private String updateUser;

    @Column(name = "CREATE_DATE_TIME")
    private LocalDateTime createDateTime;

    @Column(name = "UPDATE_DATE_TIME")
    private LocalDateTime updateDateTime;
    
    @Column(name = "ICON_TAG")
    private String iconTag;
    
    @Column(name = "MENU_URL")
    private String menuUrl;

    // ゲッターとセッター
    public String getMenuId() { return menuId; }
    public void setMenuId(String menuId) { this.menuId = menuId; }
    public String getCategoryId() { return categoryId; }
    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }
    public String getMenuName() { return menuName; }
    public void setMenuName(String menuName) { this.menuName = menuName; }
    public String getCreateUser() { return createUser; }
    public void setCreateUser(String createUser) { this.createUser = createUser; }
    public String getUpdateUser() { return updateUser; }
    public void setUpdateUser(String updateUser) { this.updateUser = updateUser; }
    public LocalDateTime getCreateDateTime() { return createDateTime; }
    public void setCreateDateTime(LocalDateTime createDateTime) { this.createDateTime = createDateTime; }
    public LocalDateTime getUpdateDateTime() { return updateDateTime; }
    public void setUpdateDateTime(LocalDateTime updateDateTime) { this.updateDateTime = updateDateTime; }
    
	public String getIconTag() {
		return iconTag;
	}
	public void setIconTag(String iconTag) {
		this.iconTag = iconTag;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
}