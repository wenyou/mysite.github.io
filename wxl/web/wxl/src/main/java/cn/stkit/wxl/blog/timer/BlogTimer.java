package cn.stkit.wxl.blog.timer;

import cn.stkit.wxl.blog.entity.BlogAccess;
import cn.stkit.wxl.blog.service.BlogAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file BlogTimer
 * @desc Blog定时器使用Spring定时任务，主要用于在设定时间插入每日访问统计的记录
 * @website http://www.stkit.cn/
 * @date 2020/7/2 9:05 上午
 */
@Component
public class BlogTimer
{
    @Autowired
    private BlogAccessService blogAccessService;

    @Scheduled(cron = "0 0 0 1/1 * ?")
    private void updateTodayBlogVisits()
    {
        BlogAccess blogAccess = new BlogAccess();
        blogAccess.setAccessCount(1);
        blogAccess.setAccessDate(new Date());
        blogAccessService.save(blogAccess);
    }
}