package com.example.guru.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.guru.dto.UserBulkImportSearchDetail;
import com.example.guru.entity.ImportHistory;

/**
 * インポート履歴のリポジトリ。
 * インポートデータの操作をサポートします。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-24
 */
@Repository
public interface ImportHistoryRepository extends JpaRepository<ImportHistory, Long> {
	
    /**
     * インポート履歴のシーケンスから次の値を取得します。
     * 
     * @return 次の取込番号
     */
    @Query(value = "SELECT USER_IMPORT_ID_SEQ.NEXTVAL FROM DUAL", nativeQuery = true)
    long getNextSequenceValue();
    
    /**
     * 検索条件に基づいてユーザー詳細情報のページを取得します。
     * 
     * @param status ステータス
     * @param importDateFrom 取込日時(開始)
     * @param importDateTo 取込日時(終了)
     * @param pageable ページング情報
     * @return インポート履歴詳細情報のページ
     */
    @Query(value = "SELECT TO_CHAR(IH.IMPORT_ID) AS IMPORT_ID, MG.NAME AS STATUS, " +
            "IH.FILE_NAME, IH.ERROR_FILE_PATH, TO_CHAR(IH.IMPORT_DATE_TIME, 'YYYY/MM/DD HH24:MI:SS') AS IMPORT_DATE_TIME " +
            "FROM IMPORT_HISTORY IH " +
            "INNER JOIN M_GENERIC MG " +
            "ON MG.CODE = IH.STATUS " +
            "AND MG.CATEGORY = 'IMPORT_STATUS' " +
            "WHERE (:importId IS NULL OR IH.IMPORT_ID = :importId) " +
            "AND (:status IS NULL OR IH.STATUS = :status) " +
            "AND (:importDateFrom IS NULL OR TO_CHAR(IH.IMPORT_DATE_TIME, 'YYYYMMDD') >= :importDateFrom) " +
            "AND (:importDateTo IS NULL OR TO_CHAR(IH.IMPORT_DATE_TIME, 'YYYYMMDD') <= :importDateTo) " +
            "ORDER BY IH.IMPORT_DATE_TIME" ,
            countQuery = "SELECT COUNT(*) " +
                         "FROM IMPORT_HISTORY " +
                         "WHERE (:importId IS NULL OR IMPORT_ID = :importId) " +
                         "AND (:status IS NULL OR STATUS = :status) " +
                         "AND (:importDateFrom IS NULL OR TO_CHAR(IMPORT_DATE_TIME, 'YYYYMMDD') >= :importDateFrom) " +
                         "AND (:importDateTo IS NULL OR TO_CHAR(IMPORT_DATE_TIME, 'YYYYMMDD') <= :importDateTo) ",
            nativeQuery = true)
    Page<UserBulkImportSearchDetail> findImportHistoryDetailsByCriteria(@Param("importId") Long importId, @Param("status") String status, 
            @Param("importDateFrom") String importDateFrom, 
            @Param("importDateTo") String importDateTo, 
            Pageable pageable);
}