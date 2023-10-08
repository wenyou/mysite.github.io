package cn.stkit.wxl.model.message.response;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file Article
 * @desc  图文model
 * @website http://www.stkit.cn/
 * @date 2020/6/11 5:44 下午
 */

public class Article
{
    //图文消息名称,图文消息标题
    private String Title;

    //图文消息描述
    private String Description;

    //图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
    private String PicUrl;

    //点击图文消息跳转链接
    private String Url;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
