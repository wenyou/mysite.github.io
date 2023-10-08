package cn.stkit.wxl.persistence.beans;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file Role
 * @desc 用户角色实体类
 * @website http://www.stkit.cn/
 * @date 2020/6/16 2:06 下午
 */

public class Role
{
    //用户角色ID
    private Integer roleId;
    //用户名
    private String roleName;
    //创建时间
    private int createdTime;
    //创建者用户ID
    private int createdUserId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(int createdTime) {
        this.createdTime = createdTime;
    }

    public int getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(int createdUserId) {
        this.createdUserId = createdUserId;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", createdTime=" + createdTime +
                ", createdUserId=" + createdUserId +
                '}';
    }
}
