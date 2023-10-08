package cn.stkit.wxl.model.message.response;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file TextMessage
 * @desc  回复文本消息
 * @website http://www.stkit.cn/
 * @date 2020/6/11 5:45 下午
 */

public class TextMessage extends BaseMessage
{
    //回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
