package cn.stkit.wxl.blog.service;

import cn.stkit.wxl.blog.entity.Tag;
import cn.stkit.wxl.blogadmin.entity.Pages;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file TagService
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/6/30 11:38 上午
 */

public interface TagService
{
    List<Tag> findAll();

    Tag findByTagName(String tagName);

    Integer countByTagInputDate(Date tagInputDate);

    int count();

    Tag saveTag(Tag tag);

    Page<Tag> findAllBySearch(Pages pages, Integer tagId, String tagName);

    void deleteTagByTagId(Integer tagId);
}