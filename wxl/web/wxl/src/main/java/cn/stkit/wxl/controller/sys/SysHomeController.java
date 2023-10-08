package cn.stkit.wxl.controller.sys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file SysHomeController
 * @desc 系统首页
 * @website http://www.stkit.cn/
 * @date 2020/6/14 5:58 下午
 */
@Controller
@RequestMapping("/")
public class SysHomeController
{

    private static final Logger logger = LoggerFactory.getLogger(SysHomeController.class);

    /**
     * 系统首页
     * @return
     */
    @GetMapping("")
    public String index(ModelMap modelMap)
    {
        logger.info("-----进入系统首页-----");
        modelMap.addAttribute("title", "STKIT-WXCMS系统首页");
        return "index";
    }
}