package com.example.guru.service;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.example.guru.client.PostcodeJpApiClient;
import com.example.guru.entity.MUser;
import com.example.guru.form.PostcodeJpForm.AddressData;
import com.example.guru.form.UserForm;
import com.example.guru.repository.MUserRepository;
import com.example.guru.util.CheckUtils;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(PostcodeJpApiClient.class);

    @Autowired
    private MUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private PostcodeJpApiClient postcodeJpApiClient; // 新規追加

    @Override
    public MUser findByUserId(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            return null;
        }
        return userRepository.findByUserId(userId.trim());
    }

    @Override
    public void registerUser(UserForm userForm, BindingResult bindingResult) {
    	
    	if(!CheckUtils.isNotBlank(userForm.getPassword())) {
    		bindingResult.addError(new FieldError("userForm", "password", "登録時はパスワードは必須です。"));
    	}
    	
    	if(!CheckUtils.isNotBlank(userForm.getConfirmPassword())) {
    		bindingResult.addError(new FieldError("userForm", "confirmPassword", "登録時はパスワード（確認）は必須です。"));
    	}
    	
        // パスワードと確認用パスワードの一致チェック
        if (!userForm.getPassword().equals(userForm.getConfirmPassword())) {
            bindingResult.addError(new FieldError("userForm", "confirmPassword", "パスワードが一致しません。"));
        }

        if (CheckUtils.isValidZipCode(userForm.getPostalCode())) {
            bindingResult.addError(new FieldError("userForm", "postalCode", "郵便番号を数字3桁-数字4桁の形式にして下さい。"));
        }

        // エラーがあれば処理を中断
        if (bindingResult.hasErrors()) {
            return;
        }
        
        // ログイン中のユーザー名を取得
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

        // エンティティに変換して保存
        MUser user = new MUser();
        user.setUserId("A" + userRepository.getNextSequenceValue());
        user.setUserName(userForm.getUserName());
        // パスワードをハッシュ化して設定
        user.setPassword(passwordEncoder.encode(userForm.getPassword()));
        user.setRoleId(userForm.getRoleId());
        user.setGender(userForm.getGender());
        user.setEmail(userForm.getEmail());
        user.setPostalCode(userForm.getPostalCode());
        user.setAddress1(userForm.getAddress1());
        user.setAddress2(userForm.getAddress2());
        user.setRemarks(userForm.getRemarks());
        user.setCreateUser(currentUser);
        user.setUpdateUser(currentUser);
        user.setCreateDateTime(new Timestamp(System.currentTimeMillis()));
        user.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));

        userRepository.save(user);

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

        // エラーがあれば処理を中断
        if (bindingResult.hasErrors()) {
            return;
        }
        
        // ログイン中のユーザー名を取得
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

        // 更新処理
        user.setUserName(userForm.getUserName());
        // パスワードをハッシュ化して設定
        if(CheckUtils.isNotBlank(userForm.getPassword())) {
        	user.setPassword(passwordEncoder.encode(userForm.getPassword()));
        }
        
        user.setRoleId(userForm.getRoleId());
        user.setGender(userForm.getGender());
        user.setEmail(userForm.getEmail());
        user.setPostalCode(userForm.getPostalCode());
        user.setAddress1(userForm.getAddress1());
        user.setAddress2(userForm.getAddress2());
        user.setRemarks(userForm.getRemarks());
        user.setUpdateUser(currentUser);
        user.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
        
        userRepository.save(user);
    }
    
    @Override
    public List<AddressData> getAddressByPostalCode(String postalCode) {
        return postcodeJpApiClient.fetchAddress(postalCode).block();
    }
}