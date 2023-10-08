package cn.stkit.wxl.model.message.response;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file BaseMessage
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/6/11 5:44 下午
 */

public class BaseMessage
{
    //接收方帐号（收到的OpenID）
    private String ToUserName;

    //开发者微信号
    private String FromUserName;

    //消息创建时间 （整型）
    private long CreateTime;

    //消息类型，文本为text，图文为news， 其他有 image、voice、music
    private String MsgType;

    //位0x0001被标志时，星标刚收到的消息
    private int FuncFlag;


    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(long createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public int getFuncFlag() {
        return FuncFlag;
    }

    public void setFuncFlag(int funcFlag) {
        FuncFlag = funcFlag;
    }
}
