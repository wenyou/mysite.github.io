package cn.stkit.wxl.blogadmin.controller;

import cn.stkit.wxl.blog.entity.BlogConfig;
import cn.stkit.wxl.blog.service.BlogConfigService;
import cn.stkit.wxl.blogadmin.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file BlogConfigController
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/7/20 4:51 下午
 */
@Controller
@RequestMapping("/blogAdmin/system")
public class BlogConfigController
{
    @Autowired
    private BlogConfigService blogConfigService;

    @GetMapping("/edit")
    public String edit(Model model)
    {
        model.addAttribute("blogConfig", blogConfigService.findBlogConfig());
        model.addAttribute("menuFlag", Constants.SYSTEM_MENU_FLAG);
        model.addAttribute("title", "系统配置");

        return "blog/admin/blogConfig";
    }

    @PostMapping("/save")
    @ResponseBody
    public void saveOrUpdateBlogConfig(@RequestBody BlogConfig blogConfig)
    {
        blogConfigService.saveBlogConfig(blogConfig);
    }
}