<?php
/**
 * 用户登陆
 * User: youwenzhang
 * Date: 17/7/25
 * Time: 下午4:54
 */
header("Content-type: text/html; charset=utf-8");
require_once "./include/ini.php";
require_once "./include/session_ini.php";
require_once "./include/func.php";
require_once "./include/wx_user.php";
require_once "./include/club_user.php";
/*require_once "./include/dbo.php";*/
require_once "./include/CMysqlPdo.php";
//var_dump(APP_PATH);exit;
$action = isset($_GET['a']) ? $_GET['a'] : null;

//登录验证
if($action == 'doLogin')
{
    $phone = trim($_POST['loginPhone']);
    if(!is_phone($phone)) {
        jump("login.php", "手机号码拼写错误！");
    }

    $dbo = new CMysqlPdo($DB_CONFIG);

    //检查手机号码是否已经注册
    $_exist_club_user_info = find_club_user_info_by_phone($phone, $dbo);
    $user_id = intval($_exist_club_user_info['user_id']);
    if(empty($_exist_club_user_info) || $user_id < 1 || $_exist_club_user_info['phone'] != $phone) {
        jump("login.php", $phone."手机号码未注册，请先注册！");
    }

    //验证密码
    $loginPassword = $_POST['loginPassword'];
    if(empty($loginPassword)) {
        jump("login.php", "请输入登录密码！");
    }
    $loginPassword = md5($loginPassword);
    $loginPassword = md5($_exist_club_user_info['psalt'].$loginPassword);

    if($loginPassword != $_exist_club_user_info['pwd']) {
        jump("login.php", "登录密码错误！");
    }

    //验证微信、openid
    //do ....

    //验证成功，注册session
    if($user_id) {
        reg_session_club_user_info($_exist_club_user_info);
        jump("user_center.php");
    }

}
?>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
    <title>会员登陆</title>
    <link rel="stylesheet" href="public/frozenui/css/frozen.css">
    <link rel="stylesheet" href="public/iconfont/iconfont.css">
    <link rel="stylesheet" href="public/layer_mobile/need/layer.css">
    <link rel="stylesheet" href="public/css/style.css">
</head>
<body>
<div class="l-r-div">
    <div class="l-r-top">
        <img src="./images/login-bg.png" />
        <div class="l-r-banner">
            <div class="ui-row-flex ui-whitespace">
                <div class="ui-col ui-col"><a href="reg.php">注册</a></div>
                <div class="ui-col ui-col current"><a href="javascript:void(0);">登录</a></div>
            </div>
        </div>
    </div>
    <div class="l-r-box">
        <div class="l-r-form">
            <div class="ui-form ui-border-t">
                <form id="login-form" action="login.php?a=doLogin" method="post" onsubmit="return check_login();">
                    <div class="ui-form-item ui-border-b">
                        <label><i class="iconfont icon-wangluo1"></i></label>
                        <input type="number" placeholder="请输入手机号" id="loginPhone" name="loginPhone"/>
                        <a href="javascript:void(0);" class="ui-icon-close"></a>
                    </div>
                    <div class="ui-form-item ui-border-b">
                        <label><i class="iconfont icon-mima"></i></label>
                        <input type="password" placeholder="密码" id="loginPassword" name="loginPassword"/>
                        <a href="javascript:void(0);" class="ui-icon-close"></a>
                    </div>
                    <div class="ui-btn-wrap">
                        <button type="submit" id="but_register" class="ui-btn-lg reg-btn">登录</button>
                    </div>
                </form>
            </div>
            <div class="forget-pwd">忘记密码，<a href="login.php?a=getpwd">找回！</a></div>
        </div>
    </div>
</div>
<script src="public/frozenui/lib/zepto.min.js"></script>
<script src="public/frozenui/js/frozen.js"></script>
<script src="public/layer_mobile/layer.js"></script>
<script src="public/js/utils.js"></script>
<script type="text/javascript">
    $(".ui-icon-close").on('tap',function (e) {
        $(this).siblings("input").val("");
    });
    function check_login() {
        var phone = $("#loginPhone").val();
        if(phone.length==0) {
            tips('请输入手机号码！');
            return false;
        }
        if(phone.length!=11) {
            tips('请输入有效的手机号码！');
            return false;
        }
        if (!ismobile(phone)){
            tips('请输入有效的手机号码！');
            return false;
        }
        return;
    }
</script>
</body>
</html>
