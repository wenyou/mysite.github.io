package cn.stkit.wxl.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file Link
 * @desc 链接实体表
 * @website http://www.stkit.cn/
 * @date 2020/6/29 10:55 上午
 */

@Entity
@Table(name = "blog_link")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Link implements Serializable
{
    //链接表主键ID，设置主键自增列。
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer linkId;

    //友情链接名称
    private String linkName;

    //友情链接地址
    private String linkUrl;

    //友情链接备注
    private String remark;

    //创建时间
    private int createdTime;

    //创建者用户ID
    private int createdUserId;

    //更新时间
    private int updatedTime;

    //更新者用户ID
    private int updatedUserId;

    @Transient
    private int loginUserId;
}