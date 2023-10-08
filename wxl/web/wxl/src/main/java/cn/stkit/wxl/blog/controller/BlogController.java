package cn.stkit.wxl.blog.controller;

import cn.stkit.wxl.blog.constants.Constants;
import cn.stkit.wxl.blog.entity.Article;
import cn.stkit.wxl.blog.entity.BlogConfig;
import cn.stkit.wxl.blog.entity.Message;
import cn.stkit.wxl.blog.entity.Tag;
import cn.stkit.wxl.blog.service.ArticleService;
import cn.stkit.wxl.blog.service.BlogConfigService;
import cn.stkit.wxl.blog.service.MessageService;
import cn.stkit.wxl.blog.service.TagService;
import cn.stkit.wxl.blog.util.HtmlSpirit;
import cn.stkit.wxl.blog.util.MarkdownToHtml;
import cn.stkit.wxl.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.parboiled.common.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file BlogController
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/6/29 2:34 下午
 */

@Controller
@RequestMapping("/blog")
public class BlogController
{
    @Autowired
    private ArticleService articleService;

    @Autowired
    private BlogConfigService blogConfigService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private TagService tagService;

    @GetMapping("")
    public String blog(Model model) {
        return this.blog(model, 1);
    }

    @GetMapping("/{pageNumber}")
    public String blog(Model model, @PathVariable Integer pageNumber)
    {
        if(pageNumber == null) {
            pageNumber = 1;
        }
        if(pageNumber < 1)
            pageNumber = 1;

        Page<Article> articlePage = articleService.findBlogArticleList(pageNumber - 1, Constants.defaultPageSize);
        List<Article> articleList = articlePage.getContent();
        articleList.forEach(article -> {
            String articleIntroduction = HtmlSpirit.delHTMLTag(article.getArticleContent());
            article.setArticleIntroduction(articleIntroduction.length() > 100 ? articleIntroduction.substring(0, 100) : articleIntroduction);
        });

        model.addAttribute("articleList", articleList);
        model.addAttribute("totalCount", articlePage.getTotalElements());
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("title", "博客列表");

        return "blog/index";
    }

    @GetMapping("/article/{id}")
    public String showArticle(Model model, @PathVariable Integer id)
    {
        Article article = articleService.findArticleByArticleId(id);
        if(article != null) {
            //article.setArticleReadingTime(article.getArticleReadingTime() + 1);
            //注：在updateArticleReadTime执行之前改变 article的任何属性的值，都将引起jpa自动执行一次自动生成的update sql，并把所有字段都更新一遍的语句。
            //    然后再重复执行一次在@Query中自定义的SQL。所以上述的 "article.setArticleReadingTime(article.getArticleReadingTime() + 1);"不能放在 "updateArticleReadTime"之前。
            articleService.updateArticleReadTime(article);

            //article.setArticleReadingTime(article.getArticleReadingTime() + 1);
            if(article.getArticleEditor() == 2) {
                article.setArticleShowContent(MarkdownToHtml.markDownToHtml(article.getArticleContent()));
            } else {
                article.setArticleShowContent(article.getArticleContent());
            }
        }
        else
        {
            article = new Article();
        }

        model.addAttribute("article", article);
        model.addAttribute("title", article.getArticleName());

        return "blog/article";
    }

    @GetMapping("/search")
    public String search(Model model, Integer pageNumber, String keyword)
    {
        if(StringUtils.isEmpty(keyword)) {
            return "blog/search";
        } else if(pageNumber == null) {
            pageNumber = 1;
        }
        if(pageNumber < 1) pageNumber = 1;

        Page<Article> articlePage = articleService.findSearchArticleList(pageNumber - 1, Constants.defaultPageSize, keyword);
        List<Article> articleList = articlePage.getContent();
        articleList.forEach(article -> {
            String articleIntroduction = HtmlSpirit.delHTMLTag(article.getArticleContent());
            article.setArticleIntroduction(articleIntroduction.length() > 100 ? articleIntroduction.substring(0, 100) : articleIntroduction);
        });

        model.addAttribute("articleList", articleList);
        model.addAttribute("totalCount", articlePage.getTotalElements());
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("keyword", keyword);
        model.addAttribute("title", "文章搜索");

        return "blog/search";
    }

    @GetMapping("/about")
    public String about(Model model)
    {
        BlogConfig blogConfig = blogConfigService.findBlogConfig();

        Article article = articleService.findArticleByArticleId(blogConfig.getAboutPageArticleId());
        if(article == null) article = new Article();

        if(article.getArticleEditor() == 2) {
            article.setArticleShowContent(MarkdownToHtml.markDownToHtml(article.getArticleContent()));
        } else {
            article.setArticleShowContent(article.getArticleContent());
        }

        model.addAttribute("article", article);
        model.addAttribute("title", "关于我们");
        return "blog/about";
    }

    @GetMapping("/contact")
    public String contact(Model model)
    {
        model.addAttribute("title", "联系我们");
        return "blog/contact";
    }

    @PostMapping("/contact/sendMail")
    @ResponseBody
    @Transactional(rollbackFor = Throwable.class)
    public String sendMessage(@RequestBody  Message message)
    {
        message.setMessageInputDate(new Date());
        message.setRead((byte)0);
        message.setReplied((byte)0);
        message.setCreatedTime(TimeUtil.getNowTimestamp());
        message.setCreatedUserId(0);
        message.setReplyContent("");
        messageService.saveMessage(message);
        return "success";
    }

    @GetMapping("/tag")
    public String tag(Model model)
    {
        model.addAttribute("tagList", tagService.findAll());
        model.addAttribute("title", "标签");
        return "blog/tag";
    }

    @GetMapping("/tag/{tagName}")
    public String tag(Model model, @PathVariable String tagName)
    {
        Tag tag = tagService.findByTagName(tagName);
        if(tag == null) tag = new Tag();

        model.addAttribute("tag", tag);
        model.addAttribute("articleList", tag.getArticleList());
        model.addAttribute("title", tag.getTagName());
        return "blog/tagArticle";
    }
}
