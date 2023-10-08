package cn.stkit.wxl.utils;


import org.apache.commons.lang.RandomStringUtils;

import java.util.Random;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file RandomUtil
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/7/22 3:05 下午
 */

public class RandomUtil
{
    /**
     * 生成指定长度的随机字符串
     * @param len
     * @return
     */
    public static String getAlphanumeric(Integer len)
    {
        if(len == null) len = 4;
        return RandomStringUtils.randomAlphanumeric(len);
    }

    /**
     * 根据指定字符串生成指定长度的随机字符串
     * @param len
     * @return
     */
    public static String getRandomStr(Integer len)
    {
        if(len == null) len = 4;
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        //指定字符串长度，拼接字符并toString
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            //获取指定长度的字符串中任意一个字符的索引值
            int number = random.nextInt(str.length());
            //根据索引值获取对应的字符
            char charAt = str.charAt(number);
            sb.append(charAt);
        }

        return sb.toString();
    }

    /**
     * 从指定的某几个位置(a-z、A-Z或是0-9)获取一个随机字符并组成字符串
     * @param len
     * @return
     */
    public static String getRandomAbc123(Integer len)
    {
        if(len == null) len = 4;

        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        //获取长度为len的字符串
        for(int i = 0; i < len; i++){
            //获取范围在3之内的索引值
            int number = random.nextInt(3);
            int result = 0;
            switch(number){
                case 0:
                    //Math.random()*25+65成成65-90的int型的整型,强转小数只取整数部分
                    result = (int)(Math.random()*25+65);  //对应A-Z 参考ASCII编码表
                    //将整型强转为char类型
                    sb.append((char)result);
                    break;
                case 1:
                    result = (int)(Math.random()*25+97);   //对应a-z
                    sb.append((char)result);
                    break;
                case 2:
                    sb.append(String.valueOf(new Random().nextInt(10)));
                    break;
            }
        }
        return sb.toString();
    }

}