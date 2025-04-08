package com.example.guru.service;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.example.guru.client.PostcodeJpApiClient;
import com.example.guru.dto.UserSearchDetail;
import com.example.guru.entity.MUser;
import com.example.guru.form.PostcodeJpForm.AddressData;
import com.example.guru.form.UserForm;
import com.example.guru.form.UserSearchForm;
import com.example.guru.repository.MUserRepository;
import com.example.guru.util.CheckUtils;

/**
 * {@link UserService} の実装クラス。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-19
 */
@Service
public class UserServiceImpl implements UserService {
	
	// ロガー
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private MUserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private PostcodeJpApiClient postcodeJpApiClient;

    @Override
    public MUser findByUserId(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            return null;
        }
        // ユーザーIDで検索
        return userRepository.findByUserId(userId);
    }

    @Override
    public void registerUser(UserForm userForm, BindingResult bindingResult) {
    	
        // パスワードの必須チェック
        if (!CheckUtils.isNotBlank(userForm.getPassword())) {
            bindingResult.addError(new FieldError("userForm", "password", "登録時はパスワードは必須です。"));
        }
        
        // パスワード（確認）の必須チェック
        if (!CheckUtils.isNotBlank(userForm.getConfirmPassword())) {
            bindingResult.addError(new FieldError("userForm", "confirmPassword", "登録時はパスワード（確認）は必須です。"));
        }
        
        // パスワードと確認用パスワードの一致チェック
        if (!userForm.getPassword().equals(userForm.getConfirmPassword())) {
            bindingResult.addError(new FieldError("userForm", "confirmPassword", "パスワードが一致しません。"));
        }
        
        // 郵便番号の形式チェック
        if (CheckUtils.isValidZipCode(userForm.getPostalCode())) {
            bindingResult.addError(new FieldError("userForm", "postalCode", "郵便番号をハイフンなしの7桁形式にして下さい。"));
        }
        
        // エラーがあれば処理を中断
        if (bindingResult.hasErrors()) {
            return;
        }
        
        // ログイン中のユーザー名を取得
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        
        // M_USER
        MUser user = new MUser();
        
        user.setUserId("A" + userRepository.getNextSequenceValue());  	    // ユーザーIDを自動生成
        user.setUserName(userForm.getUserName());						    // ユーザー名
        user.setPassword(passwordEncoder.encode(userForm.getPassword()));   // パスワードをハッシュ化
        user.setRoleId(userForm.getRoleId());							    // ロールID
        user.setGender(userForm.getGender());							    // 性別
        user.setEmail(userForm.getEmail());								    // メールアドレス
        user.setPostalCode(userForm.getPostalCode());					    // 郵便番号
        user.setAddress1(userForm.getAddress1());						    // 住所1
        user.setAddress2(userForm.getAddress2());						    // 住所2
        user.setRemarks(userForm.getRemarks());							    // 備考
        user.setCreateUser(currentUser);								    // 作成日時
        user.setUpdateUser(currentUser);								    // 更新日時
        user.setCreateDateTime(new Timestamp(System.currentTimeMillis())); // 作成日時
        user.setUpdateDateTime(new Timestamp(System.currentTimeMillis())); // 更新日時
        
        // 登録
        userRepository.save(user);
        
        // フォームにユーザーIDを設定
        userForm.setUserId(user.getUserId());
    }

    @Override
    public void updateUser(UserForm userForm, BindingResult bindingResult) {
    	
        // 既存ユーザーの存在チェック
        MUser user = findByUserId(userForm.getUserId());
        if (user == null) {
            bindingResult.addError(new FieldError("userForm", "userId", "ユーザーが見つかりません"));
            return;
        }
        
        // パスワードと確認用パスワードの一致チェック
        if (!userForm.getPassword().equals(userForm.getConfirmPassword())) {
            bindingResult.addError(new FieldError("userForm", "confirmPassword", "パスワードが一致しません。"));
        }
        
        // 郵便番号の形式チェック
        if (CheckUtils.isValidZipCode(userForm.getPostalCode())) {
            bindingResult.addError(new FieldError("userForm", "postalCode", "郵便番号をハイフンなしの7桁形式にして下さい。"));
        }
        
        // エラーがあれば処理を中断
        if (bindingResult.hasErrors()) {
            return;
        }
        
        // ログイン中のユーザー名を取得
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        
        // 更新処理
        user.setUserName(userForm.getUserName());								// ユーザー名
        
        if (CheckUtils.isNotBlank(userForm.getPassword())) {
            user.setPassword(passwordEncoder.encode(userForm.getPassword()));   // パスワードが入力されていれば更新
        }
        
        user.setRoleId(userForm.getRoleId());									// ロールID
        user.setGender(userForm.getGender());									// 性別
        user.setEmail(userForm.getEmail());										// メールアドレス
        user.setPostalCode(userForm.getPostalCode());							// 郵便番号
        user.setAddress1(userForm.getAddress1());								// 住所1
        user.setAddress2(userForm.getAddress2());								// 住所2
        user.setRemarks(userForm.getRemarks());									// 備考
        user.setUpdateUser(currentUser);										// 更新者
        user.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));		// 更新日時
        
        // 更新
        userRepository.save(user);
    }

    @Override
    public List<AddressData> getAddressByPostalCode(String postalCode) {
    	// 郵便番号から住所情報を取得
        return postcodeJpApiClient.fetchAddress(postalCode).block();
    }

    @Override
    public Page<UserSearchDetail> getUserDetailPage(UserSearchForm userSearchForm, int page, int size) {
    	
    	// ページ番号は0ベース
        Pageable pageable = PageRequest.of(page - 1, size);
        
        // 検索パラメータの準備（前方・部分一致用に % を付与）
        // ユーザー名
        String userIdParam = (userSearchForm.getUserId() != null && !userSearchForm.getUserId().isEmpty()) ? "%" + userSearchForm.getUserId() + "%" : null;
        // ユーザー名
        String userNameParam = (userSearchForm.getUserName() != null && !userSearchForm.getUserName().isEmpty()) ? "%" + userSearchForm.getUserName() + "%" : null;
        // メールアドレス
        String emailParam = (userSearchForm.getEmail() != null && !userSearchForm.getEmail().isEmpty()) ? "%" + userSearchForm.getEmail() + "%" : null;
        // 住所
        String addressParam = (userSearchForm.getAddress() != null && !userSearchForm.getAddress().isEmpty()) ? "%" + userSearchForm.getAddress() + "%" : null;
        
        // 検索条件に基づいてユーザー詳細情報を取得
        return userRepository.findUserDetailsByCriteria(userIdParam, userNameParam, emailParam, addressParam, userSearchForm.getGender(), userSearchForm.getRoleId(), pageable);
    }
    
    @Override
    public List<UserSearchDetail> getAllUserDetails(UserSearchForm userSearchForm) {
    	
    	// 検索パラメータの準備（前方・部分一致用に % を付与）
        // ユーザー名
        String userIdParam = (userSearchForm.getUserId() != null && !userSearchForm.getUserId().isEmpty()) ? "%" + userSearchForm.getUserId() + "%" : null;
        // ユーザー名
        String userNameParam = (userSearchForm.getUserName() != null && !userSearchForm.getUserName().isEmpty()) ? "%" + userSearchForm.getUserName() + "%" : null;
        // メールアドレス
        String emailParam = (userSearchForm.getEmail() != null && !userSearchForm.getEmail().isEmpty()) ? "%" + userSearchForm.getEmail() + "%" : null;
        // 住所
        String addressParam = (userSearchForm.getAddress() != null && !userSearchForm.getAddress().isEmpty()) ? "%" + userSearchForm.getAddress() + "%" : null;

        // リポジトリから全データを取得
        return userRepository.findAllUserDetailsByCriteria(userIdParam, userNameParam, emailParam, addressParam, userSearchForm.getGender(), userSearchForm.getRoleId());
    }
}