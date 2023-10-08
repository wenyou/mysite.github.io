package cn.stkit.wxl.blogadmin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file SysUser
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/7/2 4:42 下午
 */
@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser implements Serializable
{
    //用户ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    //用户名
    private String userName;

    //用户密码
    private String userPassword;

    //email
    private String email;

    //用户角色,主角色
    private int roleId;

    //用户手机号码
    private String phone;

    //用户状态:	0未激活，1正常，2冻结，3禁止登录，4黑名单，5删除
    private Byte userStatus;

    //用户头像地址
    private String userAvatar;

    //多角色
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "userRole", joinColumns = {@JoinColumn(name = "userId")}, inverseJoinColumns = {@JoinColumn(name = "roleId")})
    private List<SysRole> roleList;

    //多角色ID
    @Transient
    private List<Integer> roleIdList;

    //系统昵称
    private String nickName;

    //用户真实姓名
    private String realName;

    //用户微信昵称
    private String wechatNickname;

    //用户OpenID
    private String openId;

    //用户性别,值为1时是男性，值为2时是女性，值为0时是未知
    private byte sex;

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

    public SysUser(Integer userId,String userName, String userPassword, String email, byte userStatus,List<SysRole> roleList, String phone, Integer roleId)
    {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.email = email;
        this.userStatus= userStatus;
        this.roleList = roleList;
        this.phone = phone;
        this.roleId = roleId;
    }
}