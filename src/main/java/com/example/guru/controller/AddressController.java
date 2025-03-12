package com.example.guru.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.guru.form.PostcodeJpForm.AddressData;
import com.example.guru.service.UserService;

@Controller
public class AddressController {

    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/address/{postalCode}")
    @ResponseBody
    public List<AddressData> getAddress(@PathVariable String postalCode) {
        return userService.getAddressByPostalCode(postalCode);
    }
}