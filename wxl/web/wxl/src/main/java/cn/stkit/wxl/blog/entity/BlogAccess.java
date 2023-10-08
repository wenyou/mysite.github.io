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
 * @file BlogAccess
 * @desc 博客访问记录表
 * @website http://www.stkit.cn/
 * @date 2020/6/29 11:04 上午
 */
@Entity
@Table(name = "blog_access")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogAccess implements Serializable
{
    //博客访问记录表主键ID，设置主键自增列
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //访问记录的日期，设置字段类型为Date
    @Temporal(TemporalType.DATE)
    private Date accessDate;

    //具体访问时间
    private int accessDateTime;

    //每日访问的次数
    private Integer accessCount;

    //访问用户ID, 如果有
    private int accessUserId;

    //访问者IP地址
    private String ip;

    //访问者操作系统
    private String os;

    //访问者浏览器
    private String browser;
}