<?php
/**
 * 微信公众关注者信息
 * User: youwenzhang
 * Date: 17/7/19
 * Time: 下午5:50
 */


/**
 * 获取openid
 * @return string
 */
function get_openid()
{
    return time()."";
}


/**
 * 通过openid取得用户信息
 * @param $openid
 * @return $user_info 用户信息
 */
function get_wx_user_info($openid)
{
    $user_info = (object)array();
    if(!empty($openid)) {
        $access_token = get_access_token();
        //获取用户信息
        $user_url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" . $access_token . "&openid=" . $openid . "&lang=zh_CN";
        $result = file_get_contents($user_url);
        $user_info = json_decode($result);
    }
    return $user_info;
}