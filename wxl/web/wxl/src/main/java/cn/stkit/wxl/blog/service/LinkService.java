package cn.stkit.wxl.blog.service;

import cn.stkit.wxl.blog.entity.Link;
import cn.stkit.wxl.blogadmin.entity.Pages;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file LinkService
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/6/30 11:32 上午
 */

public interface LinkService
{
    List<Link> findAllByEnabled();

    int count();

    Page<Link> findAllBySearch(Pages pages, Integer linkId, String linkName);

    Link findLinkByLinkId(Integer linkId);

    int saveOrUpdateLink(Link link);

    void deleteLink(Integer linkId);
}
