package cn.stkit.wxl.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file ValidateUtil
 * @desc 检查工具类
 * @website http://www.stkit.cn/
 * @date 2020/6/16 7:30 下午
 */

public class ValidateUtil
{
    /**
     * 判断是否是一个手机号码
     * @param phone
     * @return
     */
    public static boolean isPhoneNum(String phone)
    {
        String regEx = "^1(3|4|5|6|7|8|9){1}(\\d{1})\\d{8}$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    /**
     * 判断是否是一个空字符串
     * @param str
     * @return true空，false非空
     */
    public static boolean isEmptyString(String str)
    {
        if(null == str) return true;
        return str.isEmpty();
    }
}

