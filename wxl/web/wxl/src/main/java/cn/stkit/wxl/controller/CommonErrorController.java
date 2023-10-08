package cn.stkit.wxl.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file CommonErrorController
 * @desc 统一处理异常
 * @website http://www.stkit.cn/
 * @date 2020/7/21 2:49 下午
 */
@RestController
public class CommonErrorController implements ErrorController
{
    private final String ERROR_PATH = "/error";

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @RequestMapping(value = ERROR_PATH)
    public String handleError()
    {
        System.out.println(getErrorPath());
        return "error";
    }
}
