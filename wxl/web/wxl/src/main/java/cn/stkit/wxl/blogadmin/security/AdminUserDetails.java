package cn.stkit.wxl.blogadmin.security;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file AdminUserDetails
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/7/7 6:25 下午
 */

public interface AdminUserDetails extends UserDetails
{
    String getPhone();
}
