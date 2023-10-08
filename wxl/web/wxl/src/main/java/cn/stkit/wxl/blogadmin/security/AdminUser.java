package cn.stkit.wxl.blogadmin.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file AdminUserDetails
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/7/7 6:01 下午
 */

public class AdminUser extends User implements AdminUserDetails
{

  private String phone;

  public AdminUser(String username, String password, Collection<? extends GrantedAuthority> authorities)
  {
    super(username, password, authorities);
  }

  public AdminUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities)
  {
    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
  }

  public String getPhone()
  {
    return phone;
  }

  public void setPhone(String phone)
  {
    this.phone = phone;
  }
}