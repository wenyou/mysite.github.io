package cn.stkit.wxl.model.message.request;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file LinkMessage
 * @desc 链接消息
 * @website http://www.stkit.cn/
 * @date 2020/6/11 5:24 下午
 */

public class LinkMessage extends BaseMessage
{
    //消息标题
    private String Title;

    //消息描述
    private String Description;

    //消息链接
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

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
