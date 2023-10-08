package cn.stkit.wxl.controller.wxl;

import cn.stkit.wxl.user.controller.IndexController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file WxlHomeController
 * @desc 微心理首页
 * @website http://www.stkit.cn/
 * @date 2020/7/16 7:20 下午
 */
@Controller
@RequestMapping("/wxl")
public class WxlHomeController
{
    private static final Logger logger = LoggerFactory.getLogger(WxlHomeController.class);
    /**
     * 微心理系统首页
     * @return
     */
    @GetMapping({"/", ""})
    public String index(Model model)
    {
        logger.info("-----进入微心理系统首页-----");
        model.addAttribute("title", "微心理系统首页");
        return "wxl/index";
    }
}
