package com.example.guru.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.guru.entity.MRoleMenuAuthority;

@Repository
public interface MRoleMenuAuthorityRepository extends JpaRepository<MRoleMenuAuthority, String> {
    @Query("SELECT a.menuId FROM MRoleMenuAuthority a WHERE a.roleId = :roleId")
    List<String> findMenuIdsByRoleId(String roleId);
}