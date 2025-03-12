package com.example.guru.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.guru.entity.MRole;

@Repository
public interface MRoleRepository extends JpaRepository<MRole, String> {
}