package com.example.guru.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

    @GetMapping("/demo")
    public String showDemo() {
        return "demo";  // demo.htmlを返す
    }
}