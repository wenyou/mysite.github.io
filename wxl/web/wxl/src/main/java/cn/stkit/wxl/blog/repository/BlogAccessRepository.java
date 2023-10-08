package cn.stkit.wxl.blog.repository;

import cn.stkit.wxl.blog.entity.BlogAccess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file BlogAccessRepository
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/6/29 5:22 下午
 */

public interface BlogAccessRepository extends JpaRepository<BlogAccess, Integer>
{
    BlogAccess getByAccessDateIs(Date accessDate);
}
