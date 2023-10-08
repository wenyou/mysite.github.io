package cn.stkit.wxl.blogadmin.service.impl;

import cn.stkit.wxl.blogadmin.service.AuthenticationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file AuthenticationServiceImpl
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/7/2 6:10 下午
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService
{

    @Override
    public Authentication getAuthentication()
    {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
