package cn.stkit.wxl.blog.service;

import cn.stkit.wxl.blog.entity.Article;
import cn.stkit.wxl.blogadmin.entity.Pages;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file ArticleService
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/6/29 11:51 上午
 */

public interface ArticleService
{
    //分页读取博客文章列表
    Page<Article> findBlogArticleList(int page, int size);

    //搜索文章
    Page<Article> findSearchArticleList(int page, int size, String keyword);

    //读取置顶博客文章列表
    List<Article> findTopArticleList();

    //通过Id取出一篇文章
    Article findArticleByArticleId(int articleId);

    Article findEnabledArticleByArticleId(int articleId);

    Integer saveArticle(Article article);

    Integer updateArticleReadTime(Article article);

    Integer countByArticleInputDate(Date articleInputDate);

    int count();

    Page<Article> findAllBySearch(Pages pages, Integer articleId, String articleName, String articleAuthors);

    int saveOrUpdateArticle(Article article);

    int updateArticleEnabled(Integer articleId, Byte enabled);

    int deleteArticle(Integer articleId);
}