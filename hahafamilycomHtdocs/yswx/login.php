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
require_once "./include/dbo.php";

$action = isset($_GET['a']) ? $_GET['a'] : null;

//登录验证
if($action == 'doLogin')
{
    $phone = trim($_POST['loginPhone']);
    if(!is_phone($phone)) {
        jump("login.php", "手机号码拼写错误！");
    }

    $dbo = new dbo();

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
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>会员登陆</title>
    <style>
        ul{list-style: none;}
        ul li{margin:1em;}
    </style>
</head>
<body>
<div style="text-align: center;font-size:3em;">
    <img src="./images/logo.jpg" />
    <div style="text-align: right;"><a href="reg.php">注册></a></div>
    <div>
        <form action="login.php?a=doLogin" method="post">
            <ul>
                <li>
                    <input type="number" placeholder="请输入手机号" id="loginPhone" name="loginPhone" style="font-size:1em;width:20em; height:3em;" />
                </li>
                <li>
                    <input type="password" placeholder="密码" id="loginPassword" name="loginPassword" style=" font-size:1em;width:20em; height:3em;" />
                </li>
            </ul>
            <div style="margin:1em;">
                <label ><a href="login.php?a=getpwd">忘记密码，找回！</a></label>
            </div>
            <button type="submit" id="but_register" style="width:10em; height:3em; font-size:1em;">登录</button>
        </form>
    </div>
</div>
</body>
</html>
