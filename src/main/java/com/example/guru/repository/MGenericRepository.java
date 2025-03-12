package com.example.guru.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.guru.entity.MGeneric;
import com.example.guru.entity.MGenericId;

public interface MGenericRepository extends JpaRepository<MGeneric, MGenericId> {
    List<MGeneric> findByCategoryOrderBySortOrder(String category);
}