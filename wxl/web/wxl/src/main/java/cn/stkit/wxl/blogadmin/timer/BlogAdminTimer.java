package cn.stkit.wxl.blogadmin.timer;

import cn.stkit.wxl.blog.entity.BlogAccess;
import cn.stkit.wxl.blog.entity.BlogConfig;
import cn.stkit.wxl.blog.service.BlogAccessService;
import cn.stkit.wxl.blog.service.BlogConfigService;
import cn.stkit.wxl.blog.service.MessageService;
import cn.stkit.wxl.blogadmin.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file BlogAdminTimer
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/7/3 3:43 下午
 */
@Component
public class BlogAdminTimer
{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BlogConfigService blogConfigService;

    @Autowired(required = false)
    private JavaMailSender javaMailSender;

    @Autowired
    private BlogAccessService blogAccessService;

    @Autowired
    private MessageService messageService;

    @Scheduled(cron = "0 0 0 1/1 * ?")
    private void sendDailyData()
    {
        String subject = "微博客每日数据";
        String text = this.getData();
        BlogConfig blogConfig = blogConfigService.findBlogConfig();
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(blogConfig.getEmailUsername());
        simpleMailMessage.setTo(blogConfig.getEmailUsername());
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        try{
            javaMailSender.send(simpleMailMessage);
            logger.info("邮件发送微博客每日数据成功！");
        } catch (Exception e) {
            logger.error("邮件发送微博客每日数据异常！", e);
        }
    }

    private String getData()
    {
        StringBuffer sb = new StringBuffer();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        BlogAccess blogAccess = blogAccessService.getByAccessDateIs(new Date());
        sb.append("日期是：");
        sb.append(sdf.format(blogAccess.getAccessDate()));
        sb.append("访问量为：");
        sb.append(blogAccess.getAccessCount());
        sb.append("未读消息有：");
        int count = messageService.countByRead(Constants.NO);
        sb.append(count);
        sb.append("条");
        return sb.toString();
    }
}
