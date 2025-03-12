package com.example.guru.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.guru.entity.MUser;
import com.example.guru.form.UserForm;
import com.example.guru.service.GenericService;
import com.example.guru.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private GenericService genericService;

    // 登録画面表示
    @GetMapping("/userRegist")
    public String showRegisterForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        model.addAttribute("actionUrl", "/user/userRegist");
        model.addAttribute("roles", genericService.getMasterDataByCategory("ROLE"));
        model.addAttribute("genders", genericService.getMasterDataByCategory("GENDER"));
        return "user_form";
    }

    // 更新画面表示（userId必須、messageはオプション）
    @GetMapping("/update/{userId}")
    public String showUpdateForm(
            @PathVariable String userId,
            @RequestParam(value = "message", required = false) String message, // messageをオプションで受け取る
            Model model) {
        MUser user = userService.findByUserId(userId);
        UserForm userForm = new UserForm();
        if (user != null) {
            userForm.setUserId(user.getUserId());
            userForm.setUserName(user.getUserName());
            userForm.setRoleId(user.getRoleId());
            userForm.setEmail(user.getEmail());
            userForm.setPostalCode(user.getPostalCode());
            userForm.setPassword(user.getPassword());
            userForm.setConfirmPassword(user.getPassword());
            userForm.setAddress1(user.getAddress1());
            userForm.setAddress2(user.getAddress2());
            userForm.setGender(user.getGender());
            userForm.setRemarks(user.getRemarks());
        }
        model.addAttribute("userForm", userForm);
        model.addAttribute("actionUrl", "/user/update");
        model.addAttribute("roles", genericService.getMasterDataByCategory("ROLE"));
        model.addAttribute("genders", genericService.getMasterDataByCategory("GENDER"));
        if (message != null) {
            model.addAttribute("message", message); // messageがあれば追加
        }
        return "user_form";
    }

    // 登録処理
    @PostMapping("/userRegist")
    public String registerUser(@Valid @ModelAttribute("userForm") UserForm userForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", genericService.getMasterDataByCategory("ROLE"));
            model.addAttribute("genders", genericService.getMasterDataByCategory("GENDER"));
            model.addAttribute("actionUrl", "/user/userRegist");
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + "userForm", bindingResult);
            return "user_form";
        }
        
        userService.registerUser(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", genericService.getMasterDataByCategory("ROLE"));
            model.addAttribute("genders", genericService.getMasterDataByCategory("GENDER"));
            model.addAttribute("actionUrl", "/user/userRegist");
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + "userForm", bindingResult);
            return "user_form";
        }
        String encodedMessage = URLEncoder.encode("ユーザー情報を登録しました。", StandardCharsets.UTF_8);
        return "redirect:/user/update/" + userForm.getUserId() + "?message=" + encodedMessage;
    }

    // 更新処理
    @PostMapping("/update")
    public String updateUser(@Valid @ModelAttribute("userForm") UserForm userForm, BindingResult bindingResult, Model model) {
        userService.updateUser(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", genericService.getMasterDataByCategory("ROLE"));
            model.addAttribute("genders", genericService.getMasterDataByCategory("GENDER"));
            model.addAttribute("actionUrl", "/user/update");
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + "userForm", bindingResult);
            return "user_form";
        }
        String encodedMessage = URLEncoder.encode("ユーザー情報を更新しました。", StandardCharsets.UTF_8);
        return "redirect:/user/update/" + userForm.getUserId() + "?message=" + encodedMessage;
    }
}