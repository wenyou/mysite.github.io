package cn.stkit.wxl.thread;

import cn.stkit.wxl.model.AccessToken;
import cn.stkit.wxl.utils.WeChatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file AccessTokenThread
 * @desc  定时获到微信access_token的线程
 * @website http://www.stkit.cn/
 * @date 2020/6/15 11:22 上午
 */
@Component
public class AccessTokenThread
{
    private static Logger logger = LoggerFactory.getLogger(AccessTokenThread.class);

    //第三方用户唯一凭证
    public static AccessToken accessToken = null;


    //7200秒执行一次, -3000提前3秒执行
    @Scheduled(fixedDelay = 2 * 3600 * 1000 - 3000)
    public void getToken() {
        accessToken = WeChatUtil.getAccessToken();
        if(null != accessToken) {
            logger.info("获取成功，accessToken: " + accessToken.getToken());
        } else {
            logger.error("获取token失败！");
        }
    }
}
