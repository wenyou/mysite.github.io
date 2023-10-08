package cn.stkit.wxl.blogadmin.config;

import cn.stkit.wxl.blogadmin.interceptors.AdminRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file AdminWebMvcConfig
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/7/2 6:04 下午
 */
@Configuration
public class AdminWebMvcConfig implements WebMvcConfigurer
{
    @Autowired
    AdminRequestInterceptor adminRequestInterceptor;

    @Override
    public void  addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(adminRequestInterceptor).excludePathPatterns("/blog/**","/assets/**","/upload/**");//.pathMatcher(this.getPathMatcher());
    }

    private PathMatcher getPathMatcher()
    {
        AntPathMatcher antPathMatcher = new AntPathMatcher();

        antPathMatcher.isPattern("/blogAdmin/**");

        /*
        antPathMatcher.isPattern("/user/001");// 返回 false
        antPathMatcher.isPattern("/user/*"); // 返回 true
        antPathMatcher.match("/user/001","/user/001");// 返回 true
        antPathMatcher.match("/user/*","/user/001");// 返回 true
        antPathMatcher.matchStart("/user/*","/user/001"); // 返回 true
        antPathMatcher.matchStart("/user/*","/user"); // 返回 true
        antPathMatcher.matchStart("/user/*","/user001"); // 返回 false
        antPathMatcher.extractPathWithinPattern("uc/profile*","uc/profile.html"); // 返回 profile.html
        antPathMatcher.combine("uc/*.html","uc/profile.html"); // uc/profile.html*/

        return antPathMatcher;
    }
}