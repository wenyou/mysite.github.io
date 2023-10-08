package cn.stkit.wxl.blog.init;

import cn.stkit.wxl.blog.entity.BlogAccess;
import cn.stkit.wxl.blog.entity.BlogConfig;
import cn.stkit.wxl.blog.service.BlogAccessService;
import cn.stkit.wxl.blog.service.BlogConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file InitBlogData
 * @desc intBlogVisitsAndConfigData这个方法只是在没有数据测试的时候报错才添加的，
 *          是在拦截器更新网站访问次数的时候，blog_access表内没有数据引起的。
 *          所以这里设置了一个方法，查询当天是否存在blog_access表的数据，如果不存在，就插入一条。
 *          博客配置表也存在同样的问题，不存在数据的话，需要插入一条默认数据来避免这个问题。
 * @website http://www.stkit.cn/
 * @date 2020/7/2 9:16 上午
 */
@Component
public class InitBlogData
{
    @Autowired
    private BlogAccessService blogAccessService;

    @Autowired
    private BlogConfigService blogConfigService;

    @PostConstruct
    public void intBlogVisitsAndConfigData()
    {
        System.out.println("======初始化Blog前台系统数据 执行InitBlogData#intBlogVisitsAndConfigData方法=====");
        //查询当日是否存在博客访问表记录，不存在则插入
        if(blogAccessService.getByAccessDateIs(new Date()) == null)
        {
            BlogAccess blogAccess = new BlogAccess();
            blogAccess.setAccessCount(1);
            blogAccess.setAccessDate(new Date());
            blogAccessService.save(blogAccess);
        }
        //查询是否存在博客配置表记录，不存在则插入
        if(blogConfigService.findBlogConfig() == null)
        {
            BlogConfig blogConfig = new BlogConfig();
            blogConfig.setId(1);
            blogConfig.setAboutPageArticleId(1);
            blogConfig.setBlogName("微博客-合肥蔓朵");
            blogConfig.setAuthorName("芊忆君子");
            blogConfig.setDomainName("stkit.cn");
            blogConfig.setRecordNumber("皖ICP备17010158号-2");
            blogConfig.setEmailUsername("80825745@qq.com");
            blogConfigService.saveBlogConfig(blogConfig);
        }
    }
}