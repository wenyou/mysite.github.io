package cn.stkit.wxl.persistence.beans;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file Feedback
 * @desc 意见反馈实体类
 * @website http://www.stkit.cn/
 * @date 2020/6/16 11:30 上午
 */

public class Feedback
{
    //留言ID
    private Integer id;
    //OpenID (留言者openid)
    private String openId;
    //用户ID (留言者)
    private int userId;
    //留言内容
    private String content;
    //留言时间
    private int createdTime;
    //是否处理（0未回复，1已回复）
    private byte replied;
    //回复内容
    private String replyContent;
    //回复者用户ID
    private int replyUserId;
    //回复时间
    private int replyTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(int createdTime) {
        this.createdTime = createdTime;
    }

    public byte getReplied()
    {
        return replied;
    }

    public void setReplied(byte replied)
    {
        this.replied = replied;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public int getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(int replyUserId) {
        this.replyUserId = replyUserId;
    }

    public int getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(int replyTime) {
        this.replyTime = replyTime;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", openId='" + openId + '\'' +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", createdTime=" + createdTime +
                ", replied=" + replied +
                ", replyContent='" + replyContent + '\'' +
                ", replyUserId=" + replyUserId +
                ", replyTime=" + replyTime +
                '}';
    }
}