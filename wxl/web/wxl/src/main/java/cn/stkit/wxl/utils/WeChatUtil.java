package cn.stkit.wxl.utils;

import cn.stkit.wxl.model.AccessToken;
import cn.stkit.wxl.model.WeChatUser;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.security.SecureRandom;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file WeChatUtil
 * @desc 微信工具类
 * @website http://www.stkit.cn/
 * @date 2020/6/14 7:38 下午
 */

public class WeChatUtil
{
    private static Logger logger = LoggerFactory.getLogger(WeChatUtil.class);

    /**
     * 发起https请求并获取结果
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET  POST)
     * @param outputStr 提交的数据
     * @return JSONObject  通过JSONObject.get(key)的方式获取json对象的属性值
     */
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr)
    {
        JSONObject jsonObject = null;
        StringBuffer sb = new StringBuffer();
        try {
            //创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new WxlX508TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new SecureRandom());
            //从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);

            //设置请求方式(GET/POST)
            httpUrlConn.setRequestMethod(requestMethod);

            if("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            }

            //当有数据需要提交时
            if(null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                //注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            //将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
               sb.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();

            //释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.fromObject(sb.toString());
        } catch (ConnectException ce) {
            logger.error("微信服务器连接超时。");
        } catch (Exception e) {
            logger.error("https request error:{}", e);
        }
        return jsonObject;
    }

    /**
     * 获取access_token  第三方用户唯一凭证
     * @return AccessToken
     */
    public static AccessToken getAccessToken()
    {
        AccessToken accessToken = null;
        JSONObject jsonObject = httpRequest(WeChatConst.ACCESS_TOKEN_URL, "GET", null);
        //如果请求成功
        if(null != jsonObject) {
            try {
                accessToken = new AccessToken();
                accessToken.setToken(jsonObject.getString("access_token"));
                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
            } catch (JSONException e) {
                accessToken = null;
                //获取token失败
                logger.error("获取token失败，errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return accessToken;
    }

    /**
     * 网页获取微信用户信息
     * @param code  code作为换取access_token的票据，每次用户授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期。
     *              code的来源：用户同意授权，获取code，方法，页面跳转到：https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
     *              如果用户同意授权，页面将跳转至 redirect_uri/?code=CODE&state=STATE。
     * @return WeChatUser
     */
    public static WeChatUser getWeChatUser(String code)
    {
        WeChatUser weChatUser = new WeChatUser();

        if(code == null || code.equals(""))
            return weChatUser;

        System.out.println("----获取 oauth2_access_token-----");

        //网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
        String access_token = null;
        //access_token接口调用凭证超时时间，单位（秒）
        int expires_in = 0;
        //用户刷新access_token
        String refresh_token = null;
        //用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID
        weChatUser.setOpenid(null);
        //用户授权的作用域，使用逗号（,）分隔
        String scope = null;

        //通过code换取网页授权access_token
        JSONObject jsonObject = WeChatUtil.httpRequest(WeChatConst.oauth2_snsapi_get_page_access_token.replace("CODE", code), "GET", null);
        //如果请求成功
        if(null != jsonObject) {
            try {
                weChatUser.setOpenid(jsonObject.getString("openid"));
                access_token = jsonObject.getString("access_token");
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

        if(access_token != null && weChatUser.getOpenid() != null && scope.indexOf("snsapi_userinfo") != -1) {
            JSONObject jsonObject_userinfo = WeChatUtil.httpRequest(
                    WeChatConst.oauth2_snsapi_get_user_info
                            .replace("ACCESS_TOKEN", access_token)
                            .replace("OPENID", weChatUser.getOpenid()),
                    "GET", null
            );

            System.out.println("jsonObject_userinfo = "+jsonObject_userinfo.toString());

            if(null != jsonObject_userinfo) {
                try {
                    weChatUser.setNickname(jsonObject_userinfo.getString("nickname"));
                    weChatUser.setSex((byte)jsonObject_userinfo.getInt("sex"));
                    weChatUser.setProvince(jsonObject_userinfo.getString("province"));
                    weChatUser.setCity(jsonObject_userinfo.getString("city"));
                    weChatUser.setCountry(jsonObject_userinfo.getString("country"));
                    weChatUser.setHeadimgurl(jsonObject_userinfo.getString("headimgurl"));
                } catch (JSONException e) {
                    //获取用户信息失败
                    logger.error("获取用户信息失败，errcode:{} errmsg:{}", jsonObject_userinfo.getInt("errcode"), jsonObject_userinfo.getString("errmsg"));
                }
            }
        }

        return weChatUser;
    }

}