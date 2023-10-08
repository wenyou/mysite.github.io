package cn.stkit.wxl.persistence.beans;

import org.apache.ibatis.type.Alias;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file User
 * @desc 用户实体类
 * @website http://www.stkit.cn/
 * @date 2020/6/16 11:59 上午
 */
@Alias("user")
public class User
{
    //用户ID
    private Integer userId;
    //用户名
    private String userName;
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
    //用户角色
    private int roleId;
    //用户手机号码
    private String phone;
    //用户密码
    private String userPassword;
    //用户状态:	0未激活，1正常，2冻结，3禁止登录，4黑名单，5删除
    private byte userStatus;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getWechatNickname() {
        return wechatNickname;
    }

    public void setWechatNickname(String wechatNickname) {
        this.wechatNickname = wechatNickname;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public byte getSex() {
        return sex;
    }

    public void setSex(byte sex) {
        this.sex = sex;
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

    public int getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(int updatedTime) {
        this.updatedTime = updatedTime;
    }

    public int getUpdatedUserId() {
        return updatedUserId;
    }

    public void setUpdatedUserId(int updatedUserId) {
        this.updatedUserId = updatedUserId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public byte getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(byte userStatus) {
        this.userStatus = userStatus;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", realName='" + realName + '\'' +
                ", wechatNickname='" + wechatNickname + '\'' +
                ", openId='" + openId + '\'' +
                ", sex=" + sex +
                ", createdTime=" + createdTime +
                ", createdUserId=" + createdUserId +
                ", updatedTime=" + updatedTime +
                ", updatedUserId=" + updatedUserId +
                ", roleId=" + roleId +
                ", phone='" + phone + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userStatus=" + userStatus +
                '}';
    }
}
