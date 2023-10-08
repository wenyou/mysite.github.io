package cn.stkit.wxl.model;

import org.apache.ibatis.type.Alias;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file WeChatUser
 * @desc 微信用户
 * @website http://www.stkit.cn/
 * @date 2020/6/16 6:05 下午
 */
@Alias("weChatUser")
public class WeChatUser
{
    private String openid;

    private String nickname;

    private byte sex;

    private String province;

    private String city;

    private String country;

    private String headimgurl;

    private int userId;

    //创建时间
    private int createdTime;

    //创建者用户ID
    private int createdUserId;

    //更新时间
    private int updatedTime;

    //更新者用户ID
    private int updatedUserId;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public byte getSex() {
        return sex;
    }

    public void setSex(byte sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "WeChatUser{" +
                "openid='" + openid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sex='" + sex + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", headimgurl='" + headimgurl + '\'' +
                ", userId=" + userId +
                ", createdTime=" + createdTime +
                ", createdUserId=" + createdUserId +
                ", updatedTime=" + updatedTime +
                ", updatedUserId=" + updatedUserId +
                '}';
    }
}