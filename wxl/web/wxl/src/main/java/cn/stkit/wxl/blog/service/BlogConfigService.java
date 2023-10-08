package cn.stkit.wxl.blog.service;

import cn.stkit.wxl.blog.entity.BlogConfig;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file BlogConfigService
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/6/30 11:04 上午
 */

public interface BlogConfigService
{

    BlogConfig findBlogConfig();

    void saveBlogConfig(BlogConfig blogConfig);
}
