package cn.stkit.wxl.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file Tag
 * @desc 标签表实体类
 * @website http://www.stkit.cn/
 * @date 2020/6/29 10:47 上午
 */
@Entity
@Table(name = "blog_tag")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag implements Serializable
{
    //标签表主键ID，设置主键自增列
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tagId;

    //标签名称
    private String tagName;

    //标签录入日期，设置字段类型为Date
    @Temporal(TemporalType.DATE)
    private Date tagInputDate;

    //设置与文章表的多对多关系
    //@ManyToMany
    @ManyToMany(cascade = { CascadeType.REFRESH})
    @JoinTable(name = "blogArticleTag", joinColumns = {@JoinColumn(name = "tagId")}, inverseJoinColumns = {@JoinColumn(name = "articleId")})
    private List<Article> articleList;

    //创建时间
    private int createdTime;

    //创建者用户ID
    private int createdUserId;

    //更新时间
    private int updatedTime;

    //更新者用户ID
    private int updatedUserId;

    public Tag(String tagName)
    {
        this.tagName = tagName;
    }

}