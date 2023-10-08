package cn.stkit.wxl.blogadmin.security;

import cn.stkit.wxl.blogadmin.constants.Constants;
import cn.stkit.wxl.blogadmin.entity.SysRole;
import cn.stkit.wxl.blogadmin.entity.SysUser;
import cn.stkit.wxl.blogadmin.repository.SysUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file UserDetailsServiceImpl
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/7/2 5:13 下午
 */
@Service("adminUserDetailsService")
public class AdminUserDetailsService implements UserDetailsService
{
    private static final Logger logger = LoggerFactory.getLogger(AdminUserDetailsService.class);

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException
    {
        System.out.println("===AdminUserDetailsService#loadUserByUsername====");
        SysUser sysUser = sysUserRepository.findByUserName(userName);
        System.out.println("===sysUser :" + sysUser);
        if(sysUser == null)
        {
            logger.info(userName + " 用户不存在！");
            throw new UsernameNotFoundException("用户不存在！");
        }
        else if(sysUser != null && !Constants.YES.equals(sysUser.getUserStatus()))
        {
            logger.info(userName + " 用户未启用，请联系管理员！");
            throw new UsernameNotFoundException("用户未启用，请联系管理员！");
        }

        //System.out.println("===数据库保存的密码 sysUser.getUserPassword() : " + sysUser.getUserPassword());
        //String pwd = passwordEncoder.encode(sysUser.getUserPassword());
        String pwd  = sysUser.getUserPassword();

        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        for(SysRole role : sysUser.getRoleList()) {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }

        //return new org.springframework.security.core.userdetails.User(sysUser.getUserName(), pwd, simpleGrantedAuthorities);

        AdminUser adminUser = new AdminUser(sysUser.getUserName(), pwd, simpleGrantedAuthorities);
        adminUser.setPhone(sysUser.getPhone());
        return adminUser;
    }
}