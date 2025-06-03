package com.tcmedicine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 页面控制器 - 处理所有HTML页面路由
 */
@Controller
public class PageController {

    /**
     * 首页
     */
    @GetMapping("/")
    public String index() {
        return "redirect:/index.html";
    }

    /**
     * 登录页面
     */
    @GetMapping("/login")
    public String login() {
        return "redirect:/login.html";
    }

    /**
     * 注册页面
     */
    @GetMapping("/register")
    public String register() {
        return "redirect:/register.html";
    }

    /**
     * 管理员后台
     */
    @GetMapping("/dashboard")
    public String dashboard() {
        return "redirect:/dashboard.html";
    }

    /**
     * 药材识别页面
     */
    @GetMapping("/predict")
    public String predict() {
        return "redirect:/predict.html";
    }

    /**
     * 药材信息页面
     */
    @GetMapping("/medicine")
    public String medicine() {
        return "redirect:/medicine.html";
    }

    /**
     * 疾病信息页面
     */
    @GetMapping("/diseases")
    public String diseases() {
        return "redirect:/diseases.html";
    }

    /**
     * 诊断页面
     */
    @GetMapping("/diagnosis")
    public String diagnosis() {
        return "redirect:/diagnosis.html";
    }

    /**
     * 退出登录 - 重定向到首页
     */
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }
} 