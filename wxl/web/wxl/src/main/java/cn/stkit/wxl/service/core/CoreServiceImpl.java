package cn.stkit.wxl.service.core;

import cn.stkit.wxl.model.message.response.Article;
import cn.stkit.wxl.model.message.response.NewsMessage;
import cn.stkit.wxl.model.message.response.TextMessage;
import cn.stkit.wxl.utils.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file CoreServiceImpl
 * @desc 核心服务类
 * @website http://www.stkit.cn/
 * @date 2020/6/11 5:06 下午
 */

@Service("coreService")
public class CoreServiceImpl implements CoreService
{
    private static Logger logger = LoggerFactory.getLogger(CoreServiceImpl.class);

    /**
     * 处理微信发来的请求，包括事件的推送
     * @param request
     * @return
     */
    @Override
    public String processRequest(HttpServletRequest request)
    {
        String respMessage = null;
        try {
            //默认返回的文本消息内容
            String respContent = "请求处理异常，请稍候尝试！";
            //xml请求解析
            Map<String, String> requestMap = MessageUtil.parseXML(request);
            //发送方账户(openID）
            String fromUserName = requestMap.get("FromUserName");
            //公众账号
            String toUserName = requestMap.get("ToUserName");
            //消息类型
            String msgType = requestMap.get("MsgType");

            long timestamp =  LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));

            //回复文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(timestamp);
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            textMessage.setFuncFlag(0);

            //创建图文消息
            NewsMessage newsMessage = new NewsMessage();
            newsMessage.setToUserName(fromUserName);
            newsMessage.setFromUserName(toUserName);
            newsMessage.setCreateTime(timestamp);
            newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
            newsMessage.setFuncFlag(0);

            List<Article> articleList = new ArrayList<>();

            //点击菜单id
            String EventKey = requestMap.get("EventKey");
            //接收文本消息内容
            String content = requestMap.get("Content");

            //自动回复文本消息
            if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                //如果用户发送表情，则回复同样表情
                if(isQqFace(content)) {
                    respContent = content;
                    textMessage.setContent(respContent);
                    //将文本消息对象转换成xml字符串
                    respMessage = MessageUtil.textMessageToXml(textMessage);
                } else {
                    //回复固定消息
                    switch (content) {
                        case "1": {
                            StringBuffer sb = new StringBuffer();
                            sb.append("您好，我是微心理客服，请回复数字选择服务：").append("\n\n");
                            sb.append("11 可查看测试单图文").append("\n");
                            sb.append("12  可测试多图文发送").append("\n");
                            sb.append("13  可测试网址").append("\n");

                            sb.append("或者您可以尝试发送表情").append("\n\n");
                            sb.append("回复“1”显示此帮助菜单").append("\n");

                            respContent = String.valueOf(sb);
                            textMessage.setContent(respContent);

                            respMessage = MessageUtil.textMessageToXml(textMessage);
                            break;
                        }
                        case "11": {
                            //测试单图文回复
                            Article article = new Article();
                            article.setTitle("这是一个图文消息测试");
                            article.setDescription("这是测试有没有换行\n\n如果有空行就代表换行成功\n\n点击图文可以跳转到系统首页");
                            //设置图片
                            article.setPicUrl("http://cliparts.co/cliparts/kiM/5K8/kiM5K8Kij.jpg");
                            article.setUrl("http://wxl.stkit.cn/wxl/");

                            articleList.add(article);

                            newsMessage.setArticleCount(articleList.size());
                            newsMessage.setArticles(articleList);

                            respMessage = MessageUtil.newsMessageToXml(newsMessage);

                            break;
                        }
                        case "12": {
                            //多图文发送
                            Article article1 = new Article();
                            article1.setTitle("紧急通知，不要捡这种钱！湛江都已经传疯了！\n");
                            article1.setDescription("");
                            article1.setPicUrl("http://www.sinaimg.cn/dy/slidenews/31_img/2016_38/28380_733695_698372.jpg");
                            article1.setUrl("http://mp.weixin.qq.com/s?__biz=MjM5Njc2OTI4NQ==&mid=2650924309&idx=1&sn=8bb6ae54d6396c1faa9182a96f30b225&chksm=bd117e7f8a66f769dc886d38ca2d4e4e675c55e6a5e01e768b383f5859e09384e485da7bed98&scene=4#wechat_redirect");

                            Article article2 = new Article();
                            article2.setTitle("湛江谁有这种女儿，请给我来一打！");
                            article2.setDescription("");
                            article2.setPicUrl("http://www.sinaimg.cn/dy/slidenews/31_img/2016_38/28380_733695_698372.jpg");
                            article2.setUrl("http://mp.weixin.qq.com/s?__biz=MjM5Njc2OTI4NQ==&mid=2650924309&idx=2&sn=d7ffc840c7e6d91b0a1c886b16797ee9&chksm=bd117e7f8a66f7698d094c2771a1114853b97dab9c172897c3f9f982eacb6619fba5e6675ea3&scene=4#wechat_redirect");

                            Article article3 = new Article();
                            article3.setTitle("以上图片我就随意放了");
                            article3.setDescription("");
                            article3.setPicUrl("http://www.sinaimg.cn/dy/slidenews/31_img/2016_38/28380_733695_698372.jpg");
                            article3.setUrl("http://mp.weixin.qq.com/s?__biz=MjM5Njc2OTI4NQ==&mid=2650924309&idx=3&sn=63e13fe558ff0d564c0da313b7bdfce0&chksm=bd117e7f8a66f7693a26853dc65c3e9ef9495235ef6ed6c7796f1b63abf1df599aaf9b33aafa&scene=4#wechat_redirect");

                            articleList.add(article1);
                            articleList.add(article2);
                            articleList.add(article3);

                            newsMessage.setArticleCount(articleList.size());
                            newsMessage.setArticles(articleList);

                            respMessage = MessageUtil.newsMessageToXml(newsMessage);
                            break;
                        }
                        case "13": {
                            //测试网址回复
                            respContent = "<a href=\"http://wxl.stkit.cn/wxl/\">系统主页</a>";
                            textMessage.setContent(respContent);
                            // 将文本消息对象转换成xml字符串
                            respMessage = MessageUtil.textMessageToXml(textMessage);
                            break;
                        }
                        default: {
                            respContent = "（这是里面的）很抱歉，微心理系统暂时无法提供此功能给您使用。\n\n回复“1”显示帮助信息\n\n"+fromUserName;
                            textMessage.setContent(respContent);
                            // 将文本消息对象转换成xml字符串
                            respMessage = MessageUtil.textMessageToXml(textMessage);
                        }
                    }
                }
            } else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
                //图片消息
                respContent = "您发送的是图片消息。";
                textMessage.setContent(respContent);
                respMessage = MessageUtil.textMessageToXml(textMessage);
            } else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
                //地理位置消息
                respContent = "您发送的是地理位置消息！";
                textMessage.setContent(respContent);
                // 将文本消息对象转换成xml字符串
                respMessage = MessageUtil.textMessageToXml(textMessage);
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
                //链接消息
                respContent = "您发送的是链接消息！";
                textMessage.setContent(respContent);
                // 将文本消息对象转换成xml字符串
                respMessage = MessageUtil.textMessageToXml(textMessage);
            } else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)){
                //语音消息
                respContent = "您发送的是语音消息！";
                textMessage.setContent(respContent);
                // 将文本消息对象转换成xml字符串
                respMessage = MessageUtil.textMessageToXml(textMessage);
            } else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) { //事件推送处理
                //事件推送处理

                //事件类型
                String eventType = requestMap.get("Event");
                //自定义菜单点击事件
                if(eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    switch (EventKey) {
                        case "11": {
                            respContent = "您怎么了";
                            break;
                        }
                        case "12": {
                            respContent = "我的咨询师";
                            break;
                        }
                        case "13": {
                            respContent = "我的咨询订单";
                            break;
                        }
                        case "21": {
                            respContent = "心理自测";
                            break;
                        }
                        case "22": {
                            respContent = "心灵导航";
                            break;
                        }
                        case "23": {
                            respContent = "我的公开课";
                            break;
                        }
                        case "31": {
                            respContent = "个人中心";
                            break;
                        }
                        case "32": {
                            respContent = "意见反馈";
                            break;
                        }
                        default: {
                            logger.error("开发者反馈: EventKey值没有找到， 它是："+EventKey);
                            respContent = "很抱歉，此按键功能正在升级无法使用。";
                        }
                    }
                    textMessage.setContent(respContent);
                    respMessage = MessageUtil.textMessageToXml(textMessage);
                } else if(eventType.equals(MessageUtil.EVENT_TYPE_VIEW)) {
                    //对于点击菜单转网页暂时不做推送
                    logger.info("==============OpenId: "+fromUserName+"  ========================");
                } else if(eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) { //订阅
                    //订阅
                    Article article = new Article();
                    article.setTitle("谢谢您的关注");
                    article.setDescription("点击图文进入心理测试");
                    article.setPicUrl("https://upload-images.jianshu.io/upload_images/54729-c35dc6f414240758.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/413/format/webp");
                    article.setUrl("http://wxl.stkit.cn/wxl/doship/test");
                    articleList.add(article);
                    newsMessage.setArticleCount(articleList.size());
                    newsMessage.setArticles(articleList);
                    respMessage = MessageUtil.newsMessageToXml(newsMessage);
                } else if(eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) { //扫描二维码
                    //扫描二维码
                    respContent = "这是已关注用户扫描二维码弹到的界面";
                    textMessage.setContent(respContent);
                    // 将文本消息对象转换成xml字符串
                    respMessage = MessageUtil.textMessageToXml(textMessage);
                } else if(eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {//取消订阅
                    //取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respMessage;
    }


    /**
     * 判断是否是QQ表情
     * @param content
     * @return
     */
    public static boolean isQqFace(String content)
    {
        boolean result = false;
        //判断QQ表情的正则表达式
        String qqfaceRegex = "/::\\)|/::~|/::B|/::\\||/:8-\\)|/::<|/::$|/::X|/::Z|/::'\\(|/::-\\||/::@|/::P|/::D|/::O|/::\\(|/::\\+|/:--b|/::Q|/::T|/:,@P|/:,@-D|/::d|/:,@o|/::g|/:\\|-\\)|/::!|/::L|/::>|/::,@|/:,@f|/::-S|/:\\?|/:,@x|/:,@@|/::8|/:,@!|/:!!!|/:xx|/:bye|/:wipe|/:dig|/:handclap|/:&-\\(|/:B-\\)|/:<@|/:@>|/::-O|/:>-\\||/:P-\\(|/::'\\||/:X-\\)|/::\\*|/:@x|/:8\\*|/:pd|/:<W>|/:beer|/:basketb|/:oo|/:coffee|/:eat|/:pig|/:rose|/:fade|/:showlove|/:heart|/:break|/:cake|/:li|/:bome|/:kn|/:footb|/:ladybug|/:shit|/:moon|/:sun|/:gift|/:hug|/:strong|/:weak|/:share|/:v|/:@\\)|/:jj|/:@@|/:bad|/:lvu|/:no|/:ok|/:love|/:<L>|/:jump|/:shake|/:<O>|/:circle|/:kotow|/:turn|/:skip|/:oY|/:#-0|/:hiphot|/:kiss|/:<&|/:&>";
        Pattern p = Pattern.compile(qqfaceRegex);
        Matcher m = p.matcher(content);
        if(m.matches()) {
            result = true;
        }
        return result;
    }
}