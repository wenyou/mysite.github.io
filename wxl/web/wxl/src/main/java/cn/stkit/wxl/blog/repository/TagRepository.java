package cn.stkit.wxl.blog.repository;

import cn.stkit.wxl.blog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file TagRepository
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/6/29 5:30 下午
 */

public interface TagRepository extends JpaRepository<Tag, Integer>, JpaSpecificationExecutor<Tag>
{
    Tag findByTagName(String tagName);

    Integer countByTagInputDate(Date tagInputDate);
}