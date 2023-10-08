package cn.stkit.wxl.blogadmin.service;

import org.springframework.security.core.Authentication;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file AuthenticationService
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/7/2 6:08 下午
 */

public interface AuthenticationService
{
    Authentication getAuthentication();
}
