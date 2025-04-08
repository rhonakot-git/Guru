package com.example.guru.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

/**
 * M_GENERIC テーブルのエンティティクラス。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-19
 */
@Entity
@Table(name = "M_GENERIC", schema = "KOTA")
@IdClass(MGenericId.class)
public class MGeneric {
    @Id
    @Column(name = "CATEGORY")
    private String category;

    @Id
    @Column(name = "CODE")
    private String code;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SORT_ORDER")
    private int sortOrder;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "UPDATE_USER")
    private String updateUser;

    @Column(name = "CREATE_DATE_TIME")
    private Timestamp createDateTime;

    @Column(name = "UPDATE_DATE_TIME")
    private Timestamp updateDateTime;

    // ゲッターとセッター
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getSortOrder() { return sortOrder; }
    public void setSortOrder(int sortOrder) { this.sortOrder = sortOrder; }
    public String getCreateUser() { return createUser; }
    public void setCreateUser(String createUser) { this.createUser = createUser; }
    public String getUpdateUser() { return updateUser; }
    public void setUpdateUser(String updateUser) { this.updateUser = updateUser; }
    public Timestamp getCreateDateTime() { return createDateTime; }
    public void setCreateDateTime(Timestamp createDateTime) { this.createDateTime = createDateTime; }
    public Timestamp getUpdateDateTime() { return updateDateTime; }
    public void setUpdateDateTime(Timestamp updateDateTime) { this.updateDateTime = updateDateTime; }
}