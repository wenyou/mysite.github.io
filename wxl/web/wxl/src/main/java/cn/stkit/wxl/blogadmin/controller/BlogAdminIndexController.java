package cn.stkit.wxl.blogadmin.controller;

import cn.stkit.wxl.blog.entity.BlogAccess;
import cn.stkit.wxl.blog.service.*;
import cn.stkit.wxl.blogadmin.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file IndexController
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/7/6 7:08 下午
 */
@Controller
@RequestMapping("/blogAdmin")
public class BlogAdminIndexController
{
    @Autowired
    private ArticleService articleService;

    @Autowired
    private TagService tagService;

    @Autowired
    private LinkService linkService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private BlogAccessService blogAccessService;

    @RequestMapping({"/", "/index", ""})
    public String index(Model model)
    {
        model.addAttribute("articleTotalCount", articleService.count());
        model.addAttribute("tagTotalCount", tagService.count());
        model.addAttribute("linkTotalCount", linkService.count());
        model.addAttribute("messageTotalCount", messageService.count());

        //blog访问量统计
        Date nowDate = new Date();
        model.addAttribute("todayBlogVisit", blogAccessService.sumBlogAccess(nowDate, null));
        model.addAttribute("weekBlogVisit", blogAccessService.sumBlogAccess(nowDate, 7));
        model.addAttribute("monthBlogVisit", blogAccessService.sumBlogAccess(nowDate, 30));
        model.addAttribute("totalBlogVisit", blogAccessService.sumBlogAccess(null, null));

        List<BlogAccess> blogAccessList = blogAccessService.findChartsBlogAccess();
        List<Integer> blogAccessCountList = blogAccessList.stream().map(BlogAccess::getAccessCount).collect(Collectors.toList());
        List<Date> blogAccessDateList = blogAccessList.stream().map(BlogAccess::getAccessDate).collect(Collectors.toList());
        model.addAttribute("blogAccessCountList", blogAccessCountList);
        model.addAttribute("blogAccessDateList", blogAccessDateList);

        model.addAttribute("menuFlag", Constants.INDEX_MENU_FLAG);
        return "blog/admin/index";
    }
}