package cn.stkit.wxl.blogadmin.security;

import cn.stkit.wxl.blogadmin.entity.SysRole;
import cn.stkit.wxl.utils.Md5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file LoginAuthenticationProvider
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/7/7 10:24 上午
 */
@Component
public class LoginAuthenticationProvider implements AuthenticationProvider
{
    private static final Logger logger = LoggerFactory.getLogger(LoginAuthenticationProvider.class);

    @Autowired
    @Qualifier("adminUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        //获取表单用户名
        String username = (String) authentication.getPrincipal();
        System.out.println("===LoginAuthenticationProvider#authenticate--获取表单用户名:" + username);
        //获取表单用户填写的密码
        String password = (String) authentication.getCredentials();
        System.out.println("===LoginAuthenticationProvider#authenticate--获取表单用户填写的密码:" + password);

        AdminUserDetails userDetails  = (AdminUserDetails)userDetailsService.loadUserByUsername(username);

        password = Md5Util.Md5Str(password, userDetails.getPhone().substring(7));

        if(!Objects.equals(password, userDetails.getPassword())) {
            logger.error("用户密码不正确！");
            throw new BadCredentialsException("用户密码不正确！");
        }
        password = passwordEncoder.encode(password);
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> aClass)
    {
        //return false
        //这里需要返回true或者aClass.equals(UsernamePasswordAuthenticationToken.class)，如果返回false再LoginAuthenticationProvider不执行
        //return true;
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
