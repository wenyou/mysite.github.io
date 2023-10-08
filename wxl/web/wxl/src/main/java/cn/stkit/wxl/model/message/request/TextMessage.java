package cn.stkit.wxl.model.message.request;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file TextMessage
 * @desc 文本消息
 * @website http://www.stkit.cn/
 * @date 2020/6/11 5:41 下午
 */

public class TextMessage extends BaseMessage
{
    //文本消息内容
    private String Content;

    public void setContent(String content)
    {
        Content = content;
    }

    public String getContent() {
        return Content;
    }
}
