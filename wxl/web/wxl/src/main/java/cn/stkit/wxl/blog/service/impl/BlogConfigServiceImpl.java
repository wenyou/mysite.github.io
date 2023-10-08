package cn.stkit.wxl.blog.service.impl;

import cn.stkit.wxl.blog.entity.BlogConfig;
import cn.stkit.wxl.blog.repository.BlogConfigRepository;
import cn.stkit.wxl.blog.service.BlogConfigService;
import cn.stkit.wxl.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file BlogConfigServiceImpl
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/6/30 11:05 上午
 */

@Service
public class BlogConfigServiceImpl implements BlogConfigService
{
    @Autowired
    private BlogConfigRepository blogConfigRepository;

    @Override
    @Cacheable(value = "blogConfig", key = "#page")
    public BlogConfig findBlogConfig()
    {
        Optional<BlogConfig> blogConfigOptional = blogConfigRepository.findById(1);
        if(blogConfigOptional.isPresent()) {
            return blogConfigOptional.get();
        }
        return null;
    }

    @Override
    public void saveBlogConfig(BlogConfig blogConfig)
    {
        if(blogConfig.getLoginUserId() != null) {
            blogConfig.setUpdatedTime(TimeUtil.getNowTimestamp());
            blogConfig.setUpdatedUserId(blogConfig.getLoginUserId());
        }
        blogConfigRepository.save(blogConfig);
    }
}