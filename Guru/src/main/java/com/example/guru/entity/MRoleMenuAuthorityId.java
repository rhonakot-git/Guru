package com.example.guru.entity;

import java.io.Serializable;

public class MRoleMenuAuthorityId implements Serializable {
    private String roleId;
    private String menuId;

    // デフォルトコンストラクタ
    public MRoleMenuAuthorityId() {}

    // パラメータ付きコンストラクタ
    public MRoleMenuAuthorityId(String roleId, String menuId) {
        this.roleId = roleId;
        this.menuId = menuId;
    }

    // GetterとSetter
    public String getRoleId() { return roleId; }
    public void setRoleId(String roleId) { this.roleId = roleId; }
    public String getMenuId() { return menuId; }
    public void setMenuId(String menuId) { this.menuId = menuId; }

    // equalsとhashCode（必須）
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MRoleMenuAuthorityId that = (MRoleMenuAuthorityId) o;
        return roleId.equals(that.roleId) && menuId.equals(that.menuId);
    }

    @Override
    public int hashCode() {
        int result = roleId.hashCode();
        result = 31 * result + menuId.hashCode();
        return result;
    }
}