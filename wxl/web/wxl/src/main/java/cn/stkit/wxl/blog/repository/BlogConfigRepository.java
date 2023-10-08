package cn.stkit.wxl.blog.repository;

import cn.stkit.wxl.blog.entity.BlogConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file BlogConfigRepository
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/6/29 5:55 下午
 */

public interface BlogConfigRepository extends JpaRepository<BlogConfig, Integer>
{
}
