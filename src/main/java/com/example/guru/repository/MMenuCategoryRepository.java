package com.example.guru.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.guru.entity.MMenuCategory;

@Repository
public interface MMenuCategoryRepository extends JpaRepository<MMenuCategory, String> {
    @Query("SELECT DISTINCT c FROM MMenuCategory c JOIN MMenu m ON c.categoryId = m.categoryId WHERE m.menuId IN :menuIds")
    List<MMenuCategory> findCategoriesByMenuIds(List<String> menuIds);
}