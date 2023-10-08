package cn.stkit.wxl.blogadmin.controller;

import cn.stkit.wxl.blog.entity.Article;
import cn.stkit.wxl.blog.service.ArticleService;
import cn.stkit.wxl.blogadmin.constants.Constants;
import cn.stkit.wxl.blogadmin.entity.Pages;
import cn.stkit.wxl.object.ResponseVO;
import cn.stkit.wxl.utils.ModelAndViewUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file ArticleController
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/7/14 9:31 上午
 */
@Controller
@RequestMapping("/blogAdmin/article")
public class ArticleController
{
    @Autowired
    private ArticleService articleService;

    @GetMapping("/list")
    public String article(Integer pageNumber, Integer articleId, String articleName, String articleAuthors, Model model)
    {
        Pages pages = Pages.defaultPages(pageNumber);
        Page<Article> articlePage = articleService.findAllBySearch(pages, articleId, articleName, articleAuthors);

        model.addAttribute("articleList", articlePage.getContent());
        model.addAttribute("totalCount", articlePage.getTotalElements());
        model.addAttribute("pageNumber", pages.getPageNumber());
        model.addAttribute("articleName", articleName);
        model.addAttribute("articleAuthors", articleAuthors);
        model.addAttribute("articleId", articleId);
        model.addAttribute("menuFlag", Constants.ARTICLE_MENU_FLAG);

        model.addAttribute("title", "文章管理");

        return "blog/admin/article";
    }

    @GetMapping("/saveOrUpdatePage")
    public String saveOrUpdatePage(Model model, Integer articleId)
    {
        Article article = new Article();
        if(articleId != null) {
            article = articleService.findArticleByArticleId(articleId);
            model.addAttribute("title", "修改文章");
        } else {
            article.setArticleId(0);
            article.setArticleEditor((byte)1);
            model.addAttribute("title", "新建文章");
        }
        model.addAttribute("article", article);
        model.addAttribute("menuFlag", Constants.ARTICLE_MENU_FLAG);

        return "blog/admin/articleEdit";
    }

    @PostMapping("/saveOrUpdate")
    @ResponseBody
    public ResponseVO saveOrUpdateArticle(@RequestBody Article article)
    {
        int saveArticleId = articleService.saveOrUpdateArticle(article);
        System.out.println("===saveArticleId:" + saveArticleId);
        return ModelAndViewUtil.success("文章保存成功！",  saveArticleId);
    }

    @PostMapping("/updateArticleEnabled")
    @ResponseBody
    public String updateArticleEnabled(@RequestParam Integer articleId, @RequestParam Byte enabled)
    {
        articleService.updateArticleEnabled(articleId, enabled);
        return "success";
    }

    @PostMapping("/deleteArticle")
    @ResponseBody
    public String deleteArticle(@RequestParam Integer articleId)
    {
        articleService.deleteArticle(articleId);
        return "success";
    }

}