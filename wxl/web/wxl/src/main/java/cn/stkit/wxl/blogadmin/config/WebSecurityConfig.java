package cn.stkit.wxl.blogadmin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file WebSecurityConfig
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/7/2 5:38 下午
 */

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
    @Qualifier("adminUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("loginAuthenticationProvider")
    private AuthenticationProvider authenticationProvider;

    /*@Bean
    public PasswordEncoder passwordEncoder()
    {
        return new Md5PasswordEncoder();
    }*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        //auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        auth.authenticationProvider(authenticationProvider);
    }

    /*
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }*/

    @Override
    public void configure(WebSecurity web)
    {
        web.ignoring().antMatchers("/blog", "/assets/images/favicon.ico");
    }

    /**
     * 权限设定
     *  分为四种角色：
     *      USER(拥有首页，文章管理，标签管理权限)
     *      ADMIN(拥有首页，文章管理，标签管理权限，友情连接管理，用户管理，消息管理权限)
     *      SYSADMIN(拥有首页，系统管理权限)
     *      SUPERADMIN(拥有全部权限)
     *      后期可以修改问动态获取
     * @param httpSecurity
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception
    {
        /*
           跨域问题：X-Frame-Options   deny
           如：Refused to display 'http://stkit.cn/assets/js/tinymce/plugins/bdmap/map.html' in a frame because it set 'X-Frame-Options' to 'deny'.

           解决方法： 增加：.headers().frameOptions().disable()

           参考：
                https://blog.csdn.net/qq_41076797/article/details/86495180
                https://blog.whsir.com/post-3919.html
        * */
        httpSecurity.csrf().disable()//关闭scrf
                .headers().frameOptions().disable().and()//解决springBoot springSecurty x-frame-options deny 问题，允许iframe跨域。
                .authorizeRequests()
                .antMatchers("/assets/**","/blogAdmin/login").permitAll()
                .antMatchers("/blogAdmin/", "/blogAdmin").hasAnyRole("USER", "ADMIN", "SYSTEMADMIN", "SUPERADMIN")
                .antMatchers("/blogAdmin/index/**").hasAnyRole("USER", "ADMIN", "SYSTEMADMIN", "SUPERADMIN")
                .antMatchers("/blogAdmin/article/**").hasAnyRole("USER","ADMIN","SUPERADMIN")
                .antMatchers("/blogAdmin/resource/**").hasAnyRole("USER","ADMIN","SUPERADMIN")
                .antMatchers("/blogAdmin/tag/**").hasAnyRole("USER","ADMIN","SUPERADMIN")
                .antMatchers("/blogAdmin/link/**").hasAnyRole("ADMIN","SUPERADMIN")
                .antMatchers("/blogAdmin/user/**").hasAnyRole("ADMIN","SUPERADMIN")
                .antMatchers("/blogAdmin/message/**").hasAnyRole("ADMIN","SUPERADMIN")
                .antMatchers("/blogAdmin/system/**").hasAnyRole("SYSTEMADMIN", "SUPERADMIN")
                .and()
                .formLogin().loginPage("/blogAdmin/login").loginProcessingUrl("/blogAdmin/login")
                //.usernameParameter("username").passwordParameter("password")
                .failureUrl("/blogAdmin/login-error").successForwardUrl("/blogAdmin/")
                .and()
                .logout().logoutUrl("/blogAdmin/logout").logoutSuccessUrl("/blogAdmin/login").permitAll().deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling().accessDeniedPage("/blogAdmin/login");
    }

    /*
    //使用不加密密码
    @Bean
    public static NoOpPasswordEncoder passwordEncoder()
    {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    //根据用户名密码实现登录
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception
    {
        //authenticationManagerBuilder.userDetailsService(sysUserDetailsService);
        //authenticationManagerBuilder.userDetailsService(sysUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
        //使用不加密密码
        authenticationManagerBuilder.userDetailsService(sysUserDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }*/

}