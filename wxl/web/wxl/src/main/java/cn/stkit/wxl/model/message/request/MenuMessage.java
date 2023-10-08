package cn.stkit.wxl.model.message.request;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file MenuMessage
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/6/11 5:40 下午
 */

public class MenuMessage extends BaseMessage
{
    private String EventKey;

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String eventKey) {
        EventKey = eventKey;
    }
}
