<?php
/**
 * Created by PhpStorm.
 * User: youwenzhang
 * Date: 17/7/19
 * Time: 上午10:37
 */
require_once "./include/ini.php";

/**
 * 获取access token，并存储起来，两个小时获取一次。
 * 获取access token，请使用这个方法，不要使用get_access_token_from_wx_pm()方法。
 * @return $access_token  access token
 */
function get_access_token()
{
    $now_time = time();
    $access_token_save_filename = APP_PATH."data/access_token.data";
    if(file_exists($access_token_save_filename))
    {
        $file_content = file_get_contents($access_token_save_filename);
        $file_content = explode("\t",$file_content);
        $get_time = $file_content[0];
        if($get_time < $now_time - 7200) {
            return get_access_token_from_wx_pm();
        } else {
            return $file_content[1];
        }
    }
    else
    {
        return get_access_token_from_wx_pm();
    }
}

/**
 * 从微信服务器上获取access token，并存储起来。
 * @return $access_token  access token
 */
function get_access_token_from_wx_pm()
{
    $token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=".APPID."&secret=".APPSECRET;
    $json = file_get_contents($token_url);
    $result = json_decode($json);
    $access_token = $result->access_token;

    $get_time = time();
    $access_token_save_filename = APP_PATH."data/access_token.data";
    if(file_exists($access_token_save_filename))
        unlink($access_token_save_filename);
    file_put_contents($access_token_save_filename, $get_time."\t".$access_token);
    return $access_token;
}

