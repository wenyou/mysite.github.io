package cn.stkit.wxl.model.message.request;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file ImageMessage
 * @desc 图片消息
 * @website http://www.stkit.cn/
 * @date 2020/6/11 5:23 下午
 */

public class ImageMessage extends BaseMessage
{
    //图片链接
    private String PicUrl;

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }
}
