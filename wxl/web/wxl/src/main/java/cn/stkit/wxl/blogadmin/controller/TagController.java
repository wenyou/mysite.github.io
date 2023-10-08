package cn.stkit.wxl.blogadmin.controller;

import cn.stkit.wxl.blog.entity.Tag;
import cn.stkit.wxl.blog.service.TagService;
import cn.stkit.wxl.blogadmin.constants.Constants;
import cn.stkit.wxl.blogadmin.entity.Pages;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file TagController
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/7/16 11:06 上午
 */
@Controller
@RequestMapping("/blogAdmin/tag")
public class TagController
{
    @Resource
    private TagService tagService;

    @GetMapping("/list")
    public String list(Integer pageNumber, Integer tagId, String tagName, Model model)
    {
        Page<Tag> tagPage = tagService.findAllBySearch(Pages.defaultPages(pageNumber) , tagId, tagName);

        model.addAttribute("tagList", tagPage.getContent());
        model.addAttribute("totalCount", tagPage.getTotalElements());
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("tagId", tagId);
        model.addAttribute("tagName", tagName);
        model.addAttribute("menuFlag", Constants.TAG_MENU_FLAG);
        model.addAttribute("title", "标签管理");

        return "blog/admin/tag";
    }

    @PostMapping("/delete")
    @ResponseBody
    public void deleteTag(@RequestParam Integer tagId)
    {
        tagService.deleteTagByTagId(tagId);
    }
}
