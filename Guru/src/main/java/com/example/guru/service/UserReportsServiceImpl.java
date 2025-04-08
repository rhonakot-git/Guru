package com.example.guru.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.guru.dto.UserReportsDto;
import com.example.guru.repository.MUserRepository;

/**
 * {@link UserService} の実装クラス。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-19
 */
@Service
public class UserReportsServiceImpl implements UserReportsService {

    @Autowired
    private MUserRepository userRepository;

    @Override
    public List<UserReportsDto> findAllUser() {
        // すべてのユーザー情報を取得。
        return userRepository.findAllUser();
    }
}