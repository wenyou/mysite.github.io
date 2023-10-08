package cn.stkit.wxl.blog.config;

import cn.stkit.wxl.blog.interceptors.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file WebMvcConfig
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/6/30 1:57 下午
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer
{
    @Autowired
    RequestInterceptor requestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        //registry.addInterceptor(requestInterceptor);
        //排除对静态资源的拦截
        registry.addInterceptor(requestInterceptor).excludePathPatterns("/blogAdmin/**","/assets/**","/upload/**");//Arrays.asList("/assets/**")
        //registry.addInterceptor(new LoginRequiredInterceptor()).excludePathPatterns(Arrays.asList("/views/**", "/res/**"));
    }

}
