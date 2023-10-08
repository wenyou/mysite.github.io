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
 * @file Article
 * @desc 文章实体表
 * @website http://www.stkit.cn/
 * @date 2020/6/28 5:08 下午
 */

@Entity
@Table(name = "blog_article")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article implements Serializable
{
    //文章主键ID，设置主键自增列。
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer articleId;

    //文章名称
    private String articleName;

    //文章内容，设置字段类型为TEXT。
    @Lob
    @Column(columnDefinition = "TEXT")
    private String articleContent;

    //编辑文章时使用的编辑器 0 TextBox，1 TinyMCE ， 2 Markdown
    private Byte articleEditor;

    //文章作者。
    private String articleAuthors;

    //文章录入日期，设置字段类型为Date。
    @Temporal(TemporalType.DATE)
    private Date articleInputDate;

    //文章阅读次数。
    private Integer articleReadingTime;

    //是否置顶。
    private Byte top;

    //是否启用（可以理解为是否发布）。
    private Byte enabled;

    //设置与标签表的多对多关系。
    //@ManyToMany
    @ManyToMany(cascade = { CascadeType.REFRESH})
    @JoinTable(name = "blogArticleTag", joinColumns = {@JoinColumn(name = "articleId")}, inverseJoinColumns = {@JoinColumn(name = "tagId")})
    private List<Tag> tagList;

    //创建时间
    private int createdTime;

    //创建者用户ID
    private int createdUserId;

    //更新时间
    private int updatedTime;

    //更新者用户ID
    private int updatedUserId;

    /**项目内使用，非数据库字段**/
    //文章对应图片
    @Transient
    private int imageNo;

    //文章简介,用于文章列表页、搜索页、标签文章页展示文章简介，将文章内容去除HTML标签内容后，截取100个字符组成。
    @Transient
    private String articleIntroduction;

    //展示文章内容,因为支持Markdown语法，所以需要将Markdown格式文章内容直接存储到数据库中，使用这个字段展示文章详细内容。
    @Transient
    private String articleShowContent;

    @Transient
    private String tagsStr;

    @Transient
    private int loginUserId;

}