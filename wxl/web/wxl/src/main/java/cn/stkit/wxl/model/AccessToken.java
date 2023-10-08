package cn.stkit.wxl.model;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file AccessToken
 * @desc 微信通用接口凭证
 * @website http://www.stkit.cn/
 * @date 2020/6/15 10:07 上午
 */

public class AccessToken
{
    //获取到的凭证
    private String token;

    //凭证有效时间，单位：秒
    private int expiresIn;

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    @Override
    public String toString() {
        return "AccessToken{" +
                "token='" + token + '\'' +
                ", expiresIn=" + expiresIn +
                '}';
    }
}
