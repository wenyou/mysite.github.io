package cn.stkit.wxl.blog.service.impl;

import cn.stkit.wxl.blog.constants.Constants;
import cn.stkit.wxl.blog.entity.Article;
import cn.stkit.wxl.blog.entity.Tag;
import cn.stkit.wxl.blog.repository.ArticleRepository;
import cn.stkit.wxl.blog.service.ArticleService;
import cn.stkit.wxl.blog.service.TagService;
import cn.stkit.wxl.blogadmin.entity.Pages;
import cn.stkit.wxl.utils.TimeUtil;
import cn.stkit.wxl.utils.ValidateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file ArticleServiceImpl
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/6/29 11:57 上午
 */

@Service
public class ArticleServiceImpl implements ArticleService
{
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TagService tagService;

    //在Service层构建分页所需的Pageable，然后调用Repository层的方法，并且在方法上加入@Cacheable注解使用缓存。
    @Override
    @Cacheable(value = "blogArticle", key = "#page")
    public Page<Article> findBlogArticleList(int page, int size)
    {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "articleId");
        return articleRepository.findAllByEnabled(Constants.YES, pageable);
    }

    @Override
    public Page<Article> findSearchArticleList(int page, int size, String keyword)
    {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "articleId");
        return articleRepository.findAll(this.getWhereClause(keyword), pageable);
    }

    @Override
    @Cacheable(value = "indexPageArticleList")
    public List<Article> findTopArticleList()
    {
        Pageable pageable = PageRequest.of(0, 6, Sort.Direction.DESC, "articleId");
        Page<Article> page = articleRepository.findAllByTopAndEnabled(Constants.YES, Constants.YES, pageable);
        List<Article> articleList = page.getContent();
        for(int i = 0; i < articleList.size(); i++) {
            Article article = articleList.get(i);
            article.setImageNo(i + 1);
        }
        return articleList;
    }

    @Override
    public Article findArticleByArticleId(int articleId)
    {
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        if(optionalArticle.isPresent()) {
            Article article = optionalArticle.get();
            List<Tag> tagList = article.getTagList();
            if(CollectionUtils.isNotEmpty(tagList)) {
                List<String> tagNameList = tagList.stream().map(e -> e.getTagName()).collect(Collectors.toList());
                article.setTagsStr(StringUtils.join(tagNameList.toArray(),","));
            }
            return article;
        }
        return null;
    }

    @Override
    public Article findEnabledArticleByArticleId(int articleId)
    {
        return articleRepository.findByEnabledAndArticleId(Constants.YES, articleId);
    }

    @Override
    public Integer saveArticle(Article article)
    {
        Article article_result = articleRepository.save(article);
        if(article_result != null) {
             return 1;
         }
         return 0;
    }

    @Override
    public Integer updateArticleReadTime(Article article) {
        if(article != null)
            return articleRepository.updateReadTime(article);
        else
            return 0;
    }

    @Override
    public Integer countByArticleInputDate(Date articleInputDate)
    {
        return articleRepository.countByArticleInputDate(articleInputDate);
    }

    @Override
    public int count()
    {
        return (int)articleRepository.count();
    }

    @Override
    public Page<Article> findAllBySearch(Pages pages, Integer articleId, String articleName, String articleAuthors)
    {
        Pageable pageable = PageRequest.of(pages.getPage(), pages.getPageSize(), Sort.Direction.DESC, "articleId");
        return articleRepository.findAll(this.getWhereClause(articleId, articleName, articleAuthors), pageable);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int saveOrUpdateArticle(Article article)
    {
        String tagsStr = article.getTagsStr();
        List<Tag> tagList = new ArrayList<>();

        Date date = new Date();
        int timestamp = TimeUtil.getNowTimestamp();

        if(StringUtils.isNotBlank(tagsStr)) {
           String[] tagNames = tagsStr.split(",");
           for(String tagName : tagNames) {
               Tag tag = tagService.findByTagName(tagName);
               if(tag == null) {
                   tag = new Tag(tagName);
                   tag.setTagInputDate(date);
                   tag.setCreatedTime(timestamp);
                   tag.setCreatedUserId(article.getLoginUserId());
               }
               tag = tagService.saveTag(tag);
               tagList.add(tag);
           }
        }
        article.setTagList(tagList);
        if(article.getArticleId() == null) {
            article.setEnabled(Constants.NO);
            article.setArticleInputDate(date);
            article.setCreatedUserId(article.getLoginUserId());
            article.setCreatedTime(timestamp);
            article.setArticleReadingTime(0);
        } else {
            article.setUpdatedTime(timestamp);
            article.setUpdatedUserId(article.getLoginUserId());
            if(article.getArticleInputDate() == null) {
                article.setArticleInputDate(date);
            }
        }

        Article saveArticle = articleRepository.save(article);
        return saveArticle.getArticleId();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int updateArticleEnabled(Integer articleId, Byte enabled)
    {
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        if(optionalArticle.isPresent()) {
            Article article = optionalArticle.get();
            article.setEnabled(enabled);
            articleRepository.save(article);
            return 1;
        }
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int deleteArticle(Integer articleId)
    {
        articleRepository.deleteById(articleId);
        return 1;
    }

    private Specification<Article> getWhereClause(Integer articleId, String articleName, String articleAuthors)
    {
        return new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(articleId != null) {
                    predicates.add(
                        criteriaBuilder.or(criteriaBuilder.equal(root.get("articleId"), articleId))
                    );
                }
                if(!StringUtils.isEmpty(articleName)) {
                    predicates.add(
                            criteriaBuilder.or(criteriaBuilder.like(root.get("articleName"), articleName + "%"))
                    );
                }
                if(!StringUtils.isEmpty(articleAuthors)) {
                    predicates.add(
                            criteriaBuilder.or(criteriaBuilder.like(root.get("articleAuthors"), articleAuthors + "%"))
                    );
                }
                Predicate[] pre = new Predicate[predicates.size()];
                return criteriaQuery.where(predicates.toArray(pre)).getRestriction();
            }
        };
    }

    /**
     * 动态生成where语句
     * @param keyword
     * @return
     */
    private Specification<Article> getWhereClause(String keyword)
    {
        return new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(StringUtils.isNotBlank(keyword)) {
                    predicates.add(
                            criteriaBuilder.and(
                                    criteriaBuilder.or(
                                            criteriaBuilder.like(root.get("articleName"), "%"+keyword+"%"),
                                            criteriaBuilder.like(root.get("articleContent"), "%"+keyword+"%")
                                    )
                            )
                    );
                }

                predicates.add(criteriaBuilder.equal(root.get("enabled"), Constants.YES));
                Predicate[] predicates_arr = new Predicate[predicates.size()];

                return criteriaQuery.where(predicates.toArray(predicates_arr)).getRestriction();
            }
        };
    }
}