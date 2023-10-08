package cn.stkit.wxl.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 *
 * @file Md5Util
 * @desc md5加密工具类
 *
 * @website http://www.stkit.cn/
 * @date 2019/9/6 9:51 上午
 */

public class Md5Util
{
    private static final Logger log = LoggerFactory.getLogger(Md5Util.class);

    /**
     * 通过盐值对字符串进行MD5加密
     * @param param 需要加密的字符串
     * @param salt 盐值
     * @return
     */
    public static String Md5Str(String param, String salt)
    {
        return Md5Str(param + salt);
    }

    /**
     * MD5 Encrypt strings.
     * @param str 需要加密的字符串
     * @return
     */
    public static String Md5Str(String str)
    {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = messageDigest.digest(str.getBytes(StandardCharsets.UTF_8));
            return toHex(md5Bytes);
        } catch(Exception e) {
            log.error("Md5 encryption string failed! The class file is Md5Util.", e);
            e.printStackTrace();
            return null;
        }
    }

    private static String toHex(byte[] bytes)
    {
        final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (int i=0; i< bytes.length; i++) {
            ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
        }
        return ret.toString();
    }
}
