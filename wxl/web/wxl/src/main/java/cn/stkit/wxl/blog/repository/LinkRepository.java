package cn.stkit.wxl.blog.repository;

import cn.stkit.wxl.blog.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file LinkRepository
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/6/29 5:32 下午
 */

public interface LinkRepository extends JpaRepository<Link, Integer>, JpaSpecificationExecutor<Link>
{

}