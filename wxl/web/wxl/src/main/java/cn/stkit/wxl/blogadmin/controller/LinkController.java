package cn.stkit.wxl.blogadmin.controller;

import cn.stkit.wxl.blog.entity.Link;
import cn.stkit.wxl.blog.service.LinkService;
import cn.stkit.wxl.blogadmin.constants.Constants;
import cn.stkit.wxl.blogadmin.entity.Pages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file LinkController
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/7/16 5:55 下午
 */
@Controller
@RequestMapping("/blogAdmin/link")
public class LinkController
{
    @Autowired
    private LinkService linkService;

    @GetMapping("/list")
    public String list(Integer pageNumber, Integer linkId, String linkName, Model model)
    {
        Page<Link> linkPage = linkService.findAllBySearch(Pages.defaultPages(pageNumber), linkId, linkName);

        model.addAttribute("linkList", linkPage.getContent());
        model.addAttribute("totalCount", linkPage.getTotalElements());
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("linkId", linkId);
        model.addAttribute("linkName", linkName);
        model.addAttribute("menuFlag", Constants.LINK_MENU_FLAG);

        return "blog/admin/link";
    }

    @GetMapping("/saveOrUpdatePage")
    public String saveOrUpdatePage(Integer linkId, Model model)
    {
        Link link = new Link();
        if(linkId != null) {
            link = linkService.findLinkByLinkId(linkId);
            model.addAttribute("title", "修改友情链接");
        } else {
            link.setLinkId(0);
            model.addAttribute("title", "添加友情链接");
        }

        model.addAttribute("link", link);
        model.addAttribute("menuFlag", Constants.LINK_MENU_FLAG);

        return "blog/admin/linkEdit";
    }

    @PostMapping("/saveOrUpdate")
    @ResponseBody
    public String saveOrUpdate(@RequestBody Link link)
    {
        linkService.saveOrUpdateLink(link);
        return "success";
    }

    @PostMapping("/delete")
    @ResponseBody
    public void deleteLink(Integer linkId)
    {
        linkService.deleteLink(linkId);
    }
}