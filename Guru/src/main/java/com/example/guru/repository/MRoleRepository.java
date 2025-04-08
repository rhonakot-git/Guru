package com.example.guru.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.guru.entity.MRole;

/**
 * ロールのリポジトリ。
 * ロールデータの操作をサポートします。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-19
 */
@Repository
public interface MRoleRepository extends JpaRepository<MRole, String> {
}