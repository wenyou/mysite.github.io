package cn.stkit.wxl.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file BlogConfig
 * @desc 博客配置表
 * @website http://www.stkit.cn/
 * @date 2020/6/29 11:11 上午
 */

@Entity
@Table(name = "blog_config")
@Data
@NoArgsConstructor
public class BlogConfig implements Serializable
{
    //博客配置表主键ID，设置主键自增列
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //博客名称
    private String blogName;

    //博主名称
    private String authorName;

    //介绍博主页面的文章ID
    private Integer aboutPageArticleId;

    //备案号
    private String recordNumber;

    //域名
    private String domainName;

    //博主邮箱地址
    private String emailUsername;

    //创建时间
    private int createdTime;

    //创建者用户ID
    private int createdUserId;

    //更新时间
    private int updatedTime;

    //更新者用户ID
    private int updatedUserId;

    public BlogConfig(Integer id, String blogName, String authorName, Integer aboutPageArticleId, String recordNumber, String domainName, String emailUsername, int createdTime, int createdUserId, int updatedTime, int updatedUserId)
    {
        this.id = id;
        this.blogName = blogName;
        this.authorName = authorName;
        this.aboutPageArticleId = aboutPageArticleId;
        this.recordNumber = recordNumber;
        this.domainName = domainName;
        this.emailUsername = emailUsername;
        this.createdTime = createdTime;
        this.createdUserId = createdUserId;
        this.updatedTime = updatedTime;
        this.updatedUserId = updatedUserId;
    }

    @Transient
    private Integer loginUserId;
}