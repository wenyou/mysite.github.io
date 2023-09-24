<?php
/**
 * 微信服务程序入口
 * User: youwenzhang
 * Date: 17/7/18
 * Time: 上午10:48
 */

header("Content-type: text/html; charset=utf-8");

require_once "./include/ini.php";
require_once "./include/get_access_token.php";
require_once "./include/wechat_callback_api.php";
require_once "./include/click_event.php";


$wechatObj = new wechat_callback_api();
if (!isset($_GET['echostr'])) {
    $wechatObj->responseMsg();
}else{
    $wechatObj->valid();
}