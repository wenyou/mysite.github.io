package cn.stkit.wxl.blogadmin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file SysRole
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/7/2 4:58 下午
 */

@Entity
@Table(name = "role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRole implements Serializable
{
    //用户角色ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    //用户名
    private String roleName;

    //is enable
    private Byte enabled;

    //创建时间
    private int createdTime;

    //创建者用户ID
    private int createdUserId;

    //is have
    @Transient
    private Byte have;

    public SysRole(Integer roleId,String roleName, Byte enabled, int createdTime, int createdUserId)
    {
        this.roleId = roleId;
        this.roleName = roleName;
        this.enabled = enabled;
        this.createdTime = createdTime;
        this.createdUserId = createdUserId;
    }
}
