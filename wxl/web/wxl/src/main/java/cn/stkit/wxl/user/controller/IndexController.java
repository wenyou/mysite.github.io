package cn.stkit.wxl.user.controller;

import cn.stkit.wxl.model.AccessToken;
import cn.stkit.wxl.service.core.CoreService;
import cn.stkit.wxl.thread.AccessTokenThread;
import cn.stkit.wxl.utils.WeChatSignUtil;
import cn.stkit.wxl.utils.WeChatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file IndexController
 * @desc 微心理微信接口操作处理
 * @website http://www.stkit.cn/
 * @date 2020/6/10 5:25 下午
 */
@RestController
@RequestMapping("/wxl")
public class IndexController
{
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private CoreService coreService;

    /**
     * 接入公众平台
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    //http://wxl.stkit.cn/wxl/signature
    @RequestMapping(value = "/signature", method = RequestMethod.GET)
    public String WeChatInterface(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        System.out.println("------开始验证微信服务号信息-----");

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        logger.info("signature : " + signature + "---- timestamp : " + timestamp + "---- nonce : "+ nonce + "--- echostr : " + echostr);

        if(WeChatSignUtil.checkSignature(signature, timestamp, nonce)) {
            System.out.println("-----验证微信服务号成功，接入成功！---");
            return echostr;
        } else {
            logger.warn("接入失败，或者不是微信服务器发过来的请求！");
            return null;
        }
    }

    /**
     * 调用核心业务类接收消息、处理消息跟推送消息
     * @param request
     * @return
     */
    @RequestMapping(value = "/signature", method = RequestMethod.POST)
    public String post(HttpServletRequest request)
    {
        System.out.println("-------post /signature----");
        String responseMessage = coreService.processRequest(request);
        logger.info("接收消息、处理消息跟推送消息");
        return responseMessage;
    }


    /**
     * test get accessToken
     * @return
     */
    @RequestMapping("/getToken")
    public String getToken()
    {
        System.out.println("-------get Token----");

        /*AccessToken accessToken = WeChatUtil.getAccessToken();
        System.out.println(accessToken);
        return accessToken.toString();
        */
       return AccessTokenThread.accessToken.getToken();
    }

    //34_izv0G3oaOW_RwSl0Sgkw8bIEwWvIUp5t2AxzS2q2sy6QDtdD97QX0Pc5DhqYw7ajbQp_CCJ26Jdc6T1fylrlyJJ1ZTd9iyzf_RVZZsyUvicd0BTKPBIMaqkA0jWlvrRcIfRcejrYiBrDfZZiWQBcAEABVG
    //34_kf81KMWwdmjgkm4no2UYoklf5AyY4DRSN_MDv7WKsjei3XVyFDC4fDXTBqeSH26qUFrUwpDR6z-Ha8AjKqhNn-2cw8-ik1dJ-VtoPGImrBcjkIPnagHRs0vsIYQ_qV9OtOzz1l8EyI_NYG1pJYMeAFAODR
}
