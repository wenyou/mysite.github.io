package cn.stkit.wxl.blogadmin.security;

import cn.stkit.wxl.utils.Md5Util;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file MD5PasswordEncoder
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/7/7 3:38 下午
 */

public class MD5PasswordEncoder implements PasswordEncoder
{

    @Override
    public String encode(CharSequence charSequence)
    {
        return  Md5Util.Md5Str(charSequence.toString());
    }

    @Override
    public boolean matches(CharSequence charSequence, String s)
    {
        return s.equals(encode(charSequence));
    }
}
