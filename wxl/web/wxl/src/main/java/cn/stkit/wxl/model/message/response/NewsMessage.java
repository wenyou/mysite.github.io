package cn.stkit.wxl.model.message.response;

import java.util.List;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file NewsMessage
 * @desc 回复图文消息
 * @website http://www.stkit.cn/
 * @date 2020/6/11 5:44 下午
 */

public class NewsMessage extends BaseMessage
{
    // 图文消息个数；当用户发送文本、图片、语音、视频、图文、地理位置这六种消息时，开发者只能回复1条图文消息；其余场景最多可回复8条图文消息
    private int ArticleCount;

    //图文消息信息，注意，如果图文数超过限制，则将只发限制内的条数,
    //多条图文消息信息，默认第一个item为大图
    private List<Article> Articles;

    public int getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(int articleCount) {
        ArticleCount = articleCount;
    }

    public List<Article> getArticles() {
        return Articles;
    }

    public void setArticles(List<Article> articles) {
        Articles = articles;
    }
}
