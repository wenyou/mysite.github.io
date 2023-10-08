package cn.stkit.wxl.blogadmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file LoginController
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/7/3 6:49 下午
 */
@Controller
@RequestMapping("/blogAdmin")
public class LoginController
{
    @RequestMapping("/login")
    public String login(Model model)
    {
        model.addAttribute("title", "管理员登录");
        return "blog/admin/login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model)
    {
        model.addAttribute("title", "管理员登录");
        model.addAttribute("login_error", "用户名或密码错误!");
        return "blog/admin/login";
    }

    @GetMapping("/logout")
    public String logoutPage (Model model)
    {
        model.addAttribute("title", "管理员登录");
        model.addAttribute("login_error", "注销成功！");
        return "blog/admin/login";
    }
}