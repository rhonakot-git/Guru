package com.example.guru;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashExample {
	 public static void main(String[] args) {
	        // BCryptPasswordEncoderのインスタンスを作成
	        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	        // ハッシュ化したいパスワード
	        String password = "59268732Ya";

	        // パスワードをハッシュ化
	        String hashedPassword = encoder.encode(password);

	        // 結果を表示
	        System.out.println("元の値: " + password);
	        System.out.println("ハッシュ値: " + hashedPassword);

	        // ハッシュ値が正しいか確認（マッチングテスト）
	        boolean matches = encoder.matches(password, hashedPassword);
	        System.out.println("ハッシュが一致するか: " + matches);
	    }
}