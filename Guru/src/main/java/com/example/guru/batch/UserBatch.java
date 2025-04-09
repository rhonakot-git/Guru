package com.example.guru.batch;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.guru.entity.MUser;
import com.example.guru.repository.MUserRepository;

/**
 * ユーザー情報のバッチ処理クラス。
 * すべてのユーザーの更新日時を現在の日時に更新します。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-04-08
 */
@Component
@Profile("batch") // batchプロファイルでのみ動作
public class UserBatch implements CommandLineRunner {

	// ログ出力用の部品
    private static final Logger logger = LoggerFactory.getLogger(UserBatch.class);

    @Autowired
    private MUserRepository userRepository;  // ユーザー情報をデータベースから操作する部品

    /**
     * バッチ処理の本体。
     * アプリが起動したら自動で動きます。
     * 
     * @param args コマンドラインから渡される引数
     * @throws Exception 何か問題が起きた場合
     */
    @Override
    @Transactional  // データベース操作を安全に実行するための設定
    public void run(String... args) throws Exception {
        try {
            // 全部のユーザーをデータベースから取ってくる
            List<MUser> users = userRepository.findAll();

            // 1人ずつ更新日時を書き換える
            for (MUser user : users) {
                user.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
            }
            userRepository.saveAll(users);  // 一括でデータベースに保存

            // 結果をログに出す（何人更新したか）
            logger.info("ユーザー情報の更新が完了しました。更新件数: {}", users.size());
        } catch (Exception e) {
            // エラーが起きたらログに記録
            logger.error("バッチ処理中にエラーが発生しました", e);
            throw e;  // 必要に応じてエラーを再スロー
        }
    }
}