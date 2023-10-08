package cn.stkit.wxl.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file WeChatSignUtil
 * @desc 微信验证服务（签名）工具类，用于接入微信公众平台
 * @website http://www.stkit.cn/
 * @date 2020/6/11 3:15 下午
 */

public class WeChatSignUtil
{
    private static final Logger logger = LoggerFactory.getLogger(WeChatSignUtil.class);

    /**
     * 验证签名 使用微信官方SHA1类
     * @param signature
     * @param timestamp
     * @param nonce
     * @param encrypt
     * @return
     * @throws AesException
     */
    public static boolean encryptCheckSignature(String signature, String timestamp, String nonce, String encrypt) throws AesException
    {
        String tmpStr =  SHA1.getSHA1(WeChatConst.TOKEN, timestamp, nonce, encrypt);
        //对比
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }

    /**
     * 验证签名
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce)
    {
        if((signature == null || signature.equals("")) || (timestamp == null || timestamp.equals("")) || (nonce == null || nonce.equals("")) ) {
            logger.warn("参数不能为空！");
            return false;
        }

        String[] arr = new String[]{WeChatConst.TOKEN, timestamp, nonce};
        //将token  timestamp nonce进行字典排序
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for(int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
            //进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        logger.info("执行微信签名加密认证");
        content = null;
        //对比
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }

    /**
     * 将字节数组转换为十六进制字符串
     * @param byteArr
     * @return
     */
    private static String byteToStr(byte[] byteArr)
    {
       /*
        String strDigest = "";
        for(int i = 0; i < byteArr.length; i++) {
            strDigest += byteToHexStr(byteArr[i]);
        }

        return strDigest;
        */

        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < byteArr.length; i++) {
            sb.append(byteToHexStr(byteArr[i]));
        }
        return sb.toString();
    }

    /**
     * 将字节转换为十六进制字符串
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte)
    {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }

}