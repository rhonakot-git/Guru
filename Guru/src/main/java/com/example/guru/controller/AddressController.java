package com.example.guru.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.guru.form.PostcodeJpForm.AddressData;
import com.example.guru.service.UserService;

/**
 * 郵便番号に基づく住所情報を取得するためのコントローラークラス。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-19
 */
@Controller
public class AddressController {

    // ユーザーサービス
    @Autowired
    private UserService userService;

    /**
     * 指定された郵便番号に対応する住所情報を取得します。
     * 
     * @param postalCode 郵便番号
     * @return 住所情報のリスト
     */
    @GetMapping("/address/{postalCode}")
    @ResponseBody
    public List<AddressData> getAddress(@PathVariable String postalCode) {
    	// 郵便番号から住所情報を取得
        return userService.getAddressByPostalCode(postalCode);
    }
}