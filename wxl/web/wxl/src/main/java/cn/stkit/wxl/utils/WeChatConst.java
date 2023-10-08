package cn.stkit.wxl.utils;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file WeChatConst
 * @desc 微信常量配置
 * @website http://www.stkit.cn/
 * @date 2020/6/15 9:49 上午
 */

public class WeChatConst
{
    /**
     * 服务器配置
     **/
    //令牌(Token)
    public static final String TOKEN = "i4eq9lsaaeqlqke09eld0lwpqw7";
    //消息加解密密钥(EncodingAESKey)
    public static final String ENCODING_AES_KEY = "YR7PuvyHktUA2bYOS1xeIPoLAX6sUPeyIn2KZ5CQ0s6";

    /**
     * 公众号开发信息
     **/
    //开发者ID(AppID)
    public static final String APP_ID = "wxeffe8a89413e1026";
    //开发者密码(AppSecret)
    public static final String APP_SECRET = "7bc3027ffc541c5aa2fe84bf5774312f";

    /**
     * 公众号接口地址
     **/
    //获取access_token的接口地址（GET） 限2000（次/天）
    public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APP_ID + "&secret=" + APP_SECRET;
    //菜单创建（POST） 限1000（次/天）
    public final static String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    //菜单查询（GET）
    public final static String MENU_GET_URL = "https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token=ACCESS_TOKEN";
    //菜单删除（GET）
    public final static String MENU_DELETE_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

    /**
     * 网页开发常用接口
     */
    //OAuth2.0鉴权
    //1.引导用户进入授权页面同意授权，获取code
    public final static String oauth2_snsapi = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+APP_ID+"&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
    //2.通过code换取网页授权access_token
    public final static String oauth2_snsapi_get_page_access_token = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+APP_ID+"&secret="+APP_SECRET+"&code=CODE&grant_type=authorization_code";
    //3.拉取用户信息
    public final static String oauth2_snsapi_get_user_info = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    /**
     * 程序默认的成功状态码
     */
    public static final int DEFAULT_SUCCESS_CODE = 200;

    /**
     * 程序默认的错误状态码
     */
    public static final int DEFAULT_ERROR_CODE = 500;
}