package cn.stkit.wxl.user.controller;


import cn.stkit.wxl.model.WeChatUser;
import cn.stkit.wxl.thread.AccessTokenThread;
import cn.stkit.wxl.utils.ModelAndViewUtil;
import cn.stkit.wxl.utils.WeChatConst;
import cn.stkit.wxl.utils.WeChatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file FeedbackController
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/6/15 2:18 下午
 */

@Controller
@RequestMapping("/wxl/feedback")
public class FeedbackController
{
    private static final Logger logger = LoggerFactory.getLogger(FeedbackController.class);


    @GetMapping("")
    public ModelAndView index(Model model, HttpServletRequest request) throws UnsupportedEncodingException
    {

        System.out.println();
        System.out.println("----进入用户留言页面-----");

        WeChatUser weChatUser = WeChatUtil.getWeChatUser(request.getParameter("code"));

        System.out.println("code = " + request.getParameter("code"));
        System.out.println("state = " + request.getParameter("state"));

        model.addAttribute("title", "意见反馈");

        model.addAttribute("token", AccessTokenThread.accessToken.getToken());

        model.addAttribute("openid", weChatUser.getOpenid());
        model.addAttribute("userinfo", weChatUser.toString());

        return ModelAndViewUtil.view("user/feedback");
    }


    /*
    @GetMapping("")
    public ModelAndView index(Model model, HttpServletRequest request) {
        String oauth2_code = request.getParameter("code");
        WeChatUser weChatUser = new WeChatUser();

        System.out.println("----进入用户留言页面-----");

        System.out.println("code = " + request.getParameter("code"));
        System.out.println("state = " + request.getParameter("state"));

        model.addAttribute("title", "意见反馈");
        model.addAttribute("token", AccessTokenThread.accessToken.getToken());
        model.addAttribute("code", oauth2_code);
        model.addAttribute("state", request.getParameter("state"));

        System.out.println("----获取 oauth2_access_token-----");
        weChatUser.setOpenid(null);
        String oauth2_access_token = null;
        String scope = null;
        String refresh_token = null;
        int expires_in = 0;
        JSONObject jsonObject = WeChatUtil.httpRequest(WeChatConst.oauth2_snsapi_get_page_access_token.replace("CODE", oauth2_code), "GET", null);
        //如果请求成功
        if(null != jsonObject) {
            try {

                weChatUser.setOpenid(jsonObject.getString("openid"));
                System.out.println("weChatUser.setOpenid(jsonObject.getString(\"openid\")) = " + jsonObject.getString("openid"));
                oauth2_access_token = jsonObject.getString("access_token");
                scope = jsonObject.getString("scope");
                refresh_token = jsonObject.getString("refresh_token");
                expires_in = jsonObject.getInt("expires_in");
            } catch (JSONException e) {
                //获取oauth2 access_token失败
                logger.error("获取oauth2 access_token失败，errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        System.out.println("jsonObject = " + jsonObject.toString());

        System.out.println("----获取用户信息-----");

        if(oauth2_access_token != null && weChatUser.getOpenid() != null) {
            JSONObject jsonObject_userinfo = WeChatUtil.httpRequest(
                    WeChatConst.oauth2_snsapi_get_user_info.replace("ACCESS_TOKEN", oauth2_access_token)
                    .replace("OPENID", weChatUser.getOpenid()), "GET", null
            );

            System.out.println("jsonObject_userinfo = "+jsonObject_userinfo.toString());

            if(null != jsonObject_userinfo) {
                try {
                    weChatUser.setNickname(jsonObject_userinfo.getString("nickname"));
                    weChatUser.setSex(jsonObject_userinfo.getString("sex"));
                    weChatUser.setProvince(jsonObject_userinfo.getString("province"));
                    weChatUser.setCity(jsonObject_userinfo.getString("city"));
                    weChatUser.setCountry(jsonObject_userinfo.getString("country"));
                    weChatUser.setHeadimgurl(jsonObject_userinfo.getString("headimgurl"));
                    weChatUser.setUnionid(jsonObject_userinfo.getString("unionid"));
                } catch (JSONException e) {
                    //获取用户信息失败
                    logger.error("获取用户信息失败，errcode:{} errmsg:{}", jsonObject_userinfo.getInt("errcode"), jsonObject_userinfo.getString("errmsg"));
                }
            }
        }


        model.addAttribute("openid", weChatUser.getOpenid());
        model.addAttribute("oauth2_access_token", oauth2_access_token);
        model.addAttribute("scope", scope);
        model.addAttribute("expires_in", expires_in);
        model.addAttribute("refresh_token", refresh_token);
        model.addAttribute("userinfo", weChatUser.toString());

        return ModelAndViewUtil.view("user/feedback");
    }
    */
}