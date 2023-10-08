package cn.stkit.wxl;

import cn.stkit.wxl.domainTest.UserTest;
import cn.stkit.wxl.persistence.beans.User;
import cn.stkit.wxl.utils.Md5Util;
import cn.stkit.wxl.utils.TimeUtil;
import cn.stkit.wxl.utils.ValidateUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file Test
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/6/11 7:49 下午
 */

public class Test {

    public static void main(String[] args) {

        //test spring IOC
        ApplicationContext context = new AnnotationConfigApplicationContext("cn.stkit.wxl.domainTest");
        UserTest userTest = (UserTest) context.getBean("userTest");
        System.out.println("===userTest:" + userTest);


        System.out.println(UUID.randomUUID().toString());
        System.out.println(UUID.randomUUID().toString());
        System.out.println(UUID.randomUUID().toString());

        System.out.println("===stampToDate(1592641523) = " + stampToDate(1592641523));

        System.out.println("===TimeUtil.getFmtDateTimeFromTimeStamp(1592641523) =" + TimeUtil.getFmtDateTimeFromTimeStamp(1592641523, "dd/MM yy HH:mm"));


        System.out.println("1234567 md5 = "+Md5Util.Md5Str("1234567", "8805"));

        System.out.println("new Date() = " + new Date());
        System.out.println("================================");

        long s =  new Date().getTime();
        System.out.println(s);
        System.out.println("----------");

        LocalDate startTime = LocalDate.now();
        System.out.println(startTime);

        System.out.println("----------");

        Long second = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        System.out.println(second);
        System.out.println("----------");

        System.out.println("TimeUtil.getNowTimestamp() = " + TimeUtil.getNowTimestamp());

        String str = null;
        System.out.println("ValidateUtil.isEmptyString(str) = " + ValidateUtil.isEmptyString(str));

        User user = new User();
        System.out.println("ValidateUtil.isEmptyString(user.getPhone()) = " + ValidateUtil.isEmptyString(user.getPhone()));
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(int time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time_Date = sdf.format(new Date(time * 1000L));
        return time_Date;

    }
}
