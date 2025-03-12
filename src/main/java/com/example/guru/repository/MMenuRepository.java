package com.example.guru.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.guru.entity.MMenu;

@Repository
public interface MMenuRepository extends JpaRepository<MMenu, String> {
    @Query("SELECT m FROM MMenu m WHERE m.categoryId = :categoryId AND m.menuId IN :menuIds")
    List<MMenu> findMenusByCategoryAndIds(String categoryId, List<String> menuIds);
}