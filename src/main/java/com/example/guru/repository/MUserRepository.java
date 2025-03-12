package com.example.guru.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.guru.entity.MUser;

@Repository
public interface MUserRepository extends JpaRepository<MUser, String> {
    MUser findByUserId(String userId);
    
    // シーケンスの次の値を取得するメソッドを追加
    @Query(value = "SELECT LPAD(USER_ID_SEQ.NEXTVAL, 9, '0') FROM DUAL", nativeQuery = true)
    String getNextSequenceValue(); // メソッド名は任意でOK
}