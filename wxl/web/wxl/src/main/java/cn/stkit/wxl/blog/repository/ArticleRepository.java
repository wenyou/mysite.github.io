package cn.stkit.wxl.blog.repository;

import cn.stkit.wxl.blog.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file ArticleRepository
 * @desc 文章数据操作接口
 * @website http://www.stkit.cn/
 * @date 2020/6/29 11:40 上午
 */

public interface ArticleRepository extends JpaRepository<Article, Integer> , JpaSpecificationExecutor<Article>
{
    //分页查询已经发布的文章列表
    Page<Article> findAllByEnabled(Byte enabled, Pageable pageable);

    //分页查询已经发布并且置顶的文章列表
    Page<Article> findAllByTopAndEnabled(Byte top, Byte enabled, Pageable pageable);

    Article findByEnabledAndArticleId(Byte enabled, Integer articleId);

    int countByArticleInputDate(Date articleInputDate);

    @Modifying
    @Transactional
    @Query("UPDATE Article SET articleReadingTime = articleReadingTime + 1 WHERE articleId = :#{#article.articleId}")
    //@Query("UPDATE Article SET articleReadingTime = :#{#article.articleReadingTime} WHERE articleId = :#{#article.articleId}")
    //@Query(value = "UPDATE blog_article SET article_reading_time = :#{#article.articleReadingTime} WHERE article_id = :#{#article.articleId}", nativeQuery = true)
    //@Query(value = "UPDATE blog_article SET article_reading_time = article_reading_time + 1 WHERE article_id = :#{#article.articleId}", nativeQuery = true)
    int updateReadTime(@Param("article") Article article);

    /*
        @Transactional
        @Modifying(clearAutomatically = true)
        @Query(value = "update info p set p.status =?1 where p.id = ?2",nativeQuery = true)
        int updateStatusById( String status,  String id);
     */

    /*
        @Modifying
        @Query("update EARAttachment ear set ear.status = ?1 where ear.id = ?2")
        int setStatusForEARAttachment(Integer status, Long id);

        或者：

        @Modifying
        @Query("update EARAttachment ear set ear.status = :status where ear.id = :id")
        int setStatusForEARAttachment(@Param("status") Integer status, @Param("id") Long id);

     */
}
