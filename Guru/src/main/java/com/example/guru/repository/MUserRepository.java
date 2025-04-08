package com.example.guru.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.guru.dto.UserReportsDto;
import com.example.guru.dto.UserSearchDetail;
import com.example.guru.entity.MUser;

/**
 * ユーザーのリポジトリ。
 * ユーザーデータの操作およびカスタムクエリをサポートします。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-19
 */
@Repository
public interface MUserRepository extends JpaRepository<MUser, String> {

    /**
     * ユーザーIDに基づいてユーザー情報を取得します。
     * 
     * @param userId ユーザーID
     * @return ユーザー情報（存在しない場合は null）
     */
    MUser findByUserId(String userId);

    /**
     * ユーザーIDのシーケンスから次の値を取得します。
     * 
     * @return 次のユーザーID（LPADで9桁に整形）
     */
    @Query(value = "SELECT LPAD(USER_ID_SEQ.NEXTVAL, 9, '0') FROM DUAL", nativeQuery = true)
    String getNextSequenceValue();

    /**
     * 検索条件に基づいてユーザー詳細情報のページを取得します。
     * 
     * @param userId ユーザーID（部分一致）
     * @param userName ユーザー名（部分一致）
     * @param email メールアドレス（部分一致）
     * @param address 住所（部分一致）
     * @param gender 性別
     * @param roleId ロールID
     * @param pageable ページング情報
     * @return ユーザー詳細情報のページ
     */
    @Query(value = 
    		"SELECT " +
    	    "    MU.USER_ID " +
    	    "  , MU.USER_NAME " +
    	    "  , MU.ROLE_ID " +
    	    "  , MG1.NAME AS ROLE_NAME " +
    	    "  , MU.GENDER " +
    	    "  , MG2.NAME AS GENDER_NAME " +
    	    "  , MU.EMAIL " +
    	    "  , MU.POSTAL_CODE " +
    	    "  , MU.ADDRESS1 " +
    	    "  , MU.ADDRESS2 " +
    	    "  , MU.REMARKS " +
            "FROM M_USER MU " +
            "LEFT JOIN M_GENERIC MG1 ON MU.ROLE_ID = MG1.CODE AND MG1.CATEGORY = 'ROLE' " +
            "LEFT JOIN M_GENERIC MG2 ON MU.GENDER = MG2.CODE AND MG2.CATEGORY = 'GENDER' " +
            "WHERE (:userId IS NULL OR MU.USER_ID LIKE :userId) " +
            "AND (:userName IS NULL OR MU.USER_NAME LIKE :userName) " +
            "AND (:email IS NULL OR MU.EMAIL LIKE :email) " +
            "AND (:gender IS NULL OR MU.GENDER = :gender) " +
            "AND (:roleId IS NULL OR MU.ROLE_ID = :roleId) " +
            "AND (:address IS NULL OR MU.ADDRESS1 || MU.ADDRESS2 LIKE :address) " +
            "ORDER BY MU.USER_ID",
            countQuery = "SELECT COUNT(*) " +
                         "FROM M_USER MU " +
                         "WHERE (:userId IS NULL OR MU.USER_ID LIKE :userId) " +
                         "AND (:userName IS NULL OR MU.USER_NAME LIKE :userName) " +
                         "AND (:email IS NULL OR MU.EMAIL LIKE :email) " +
                         "AND (:gender IS NULL OR MU.GENDER = :gender) " +
                         "AND (:roleId IS NULL OR MU.ROLE_ID = :roleId) " +
                         "AND (:address IS NULL OR MU.ADDRESS1 || MU.ADDRESS2 LIKE :address) ",
            nativeQuery = true)
    Page<UserSearchDetail> findUserDetailsByCriteria(@Param("userId") String userId, 
            @Param("userName") String userName, 
            @Param("email") String email, 
            @Param("address") String address, 
            @Param("gender") String gender, 
            @Param("roleId") String roleId, 
            Pageable pageable);
    
    /**
     * 検索条件に基づいてユーザー詳細情報を取得します。
     * 
     * @param userId ユーザーID（部分一致）
     * @param userName ユーザー名（部分一致）
     * @param email メールアドレス（部分一致）
     * @param address 住所（部分一致）
     * @param gender 性別
     * @param roleId ロールID
     * @param pageable ページング情報
     * @return ユーザー詳細情報のページ
     */
    @Query(value = 
    		"SELECT " +
    	    "    MU.USER_ID " +
    	    "  , MU.USER_NAME " +
    	    "  , MU.ROLE_ID " +
    	    "  , MG1.NAME AS ROLE_NAME " +
    	    "  , MU.GENDER " +
    	    "  , MG2.NAME AS GENDER_NAME " +
    	    "  , MU.EMAIL " +
    	    "  , MU.POSTAL_CODE " +
    	    "  , MU.ADDRESS1 " +
    	    "  , MU.ADDRESS2 " +
    	    "  , MU.REMARKS " +
            "FROM M_USER MU " +
            "LEFT JOIN M_GENERIC MG1 ON MU.ROLE_ID = MG1.CODE AND MG1.CATEGORY = 'ROLE' " +
            "LEFT JOIN M_GENERIC MG2 ON MU.GENDER = MG2.CODE AND MG2.CATEGORY = 'GENDER' " +
            "WHERE (:userId IS NULL OR MU.USER_ID LIKE :userId) " +
            "AND (:userName IS NULL OR MU.USER_NAME LIKE :userName) " +
            "AND (:email IS NULL OR MU.EMAIL LIKE :email) " +
            "AND (:gender IS NULL OR MU.GENDER = :gender) " +
            "AND (:roleId IS NULL OR MU.ROLE_ID = :roleId) " +
            "AND (:address IS NULL OR MU.ADDRESS1 || MU.ADDRESS2 LIKE :address) " +
            "ORDER BY MU.USER_ID",
            nativeQuery = true)
    List<UserSearchDetail> findAllUserDetailsByCriteria(
            @Param("userId") String userId,
            @Param("userName") String userName,
            @Param("email") String email,
            @Param("address") String address,
            @Param("gender") String gender,
            @Param("roleId") String roleId);
    
    /**
     * すべてのユーザー情報を取得します。
     * 
     * @return ユーザー情報のページ
     */
    @Query(value = 
    		"SELECT " +
    	    "    MU.USER_ID " +
    	    "  , MU.USER_NAME " +
    	    "  , MG1.NAME AS ROLE " +
    	    "  , MG2.NAME AS GENDER " +
    	    "  , MU.EMAIL " +
    	    "  , MU.POSTAL_CODE " +
    	    "  , MU.ADDRESS1 || MU.ADDRESS2 AS ADDRESS " +
            "FROM M_USER MU " +
            "LEFT JOIN M_GENERIC MG1 ON MU.ROLE_ID = MG1.CODE AND MG1.CATEGORY = 'ROLE' " +
            "LEFT JOIN M_GENERIC MG2 ON MU.GENDER = MG2.CODE AND MG2.CATEGORY = 'GENDER' " +
            "ORDER BY MU.USER_ID",
            nativeQuery = true)
    List<UserReportsDto> findAllUser();
}