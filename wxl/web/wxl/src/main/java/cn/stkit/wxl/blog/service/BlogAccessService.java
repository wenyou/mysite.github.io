package cn.stkit.wxl.blog.service;

import cn.stkit.wxl.blog.entity.BlogAccess;

import java.util.Date;
import java.util.List;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file BlogAccessService
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/6/29 5:25 下午
 */

public interface BlogAccessService
{
    BlogAccess getByAccessDateIs(Date accessDate);

    void save(BlogAccess blogAccess);

    int sumBlogAccess(Date date, Integer days);

    List<BlogAccess> findChartsBlogAccess();
}
