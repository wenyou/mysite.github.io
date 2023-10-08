package cn.stkit.wxl.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file Message
 * @desc 访客留言消息表
 * @website http://www.stkit.cn/
 * @date 2020/6/29 10:57 上午
 */

@Entity
@Table(name = "blog_message")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable
{
    //消息表主键ID，设置主键自增列
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer messageId;

    //发消息人的邮箱地址
    private String email;

    //发消息人的名称
    private String name;

    //消息的主题
    private String subject;

    //消息的内容，设置字段类型为TEXT
    @Lob
    @Column(columnDefinition="TEXT")
    private String messageContent;

    //消息发送的时间
    private Date messageInputDate;

    //是否已读
    private byte read;

    private int createdTime;

    private int createdUserId;

    //是否处理（0未回复，1已回复）
    private byte replied;

    //回复内容
    private String replyContent;

    //回复者用户ID
    private int replyUserId;

    //回复时间
    private int replyTime;

    @Transient
    private int loginUserId;
}