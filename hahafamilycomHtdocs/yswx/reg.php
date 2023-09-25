<?php
/**
 * 注册成为俱乐部会员
 * User: youwenzhang
 * Date: 17/7/20
 * Time: 下午1:13
 */
header("Content-type: text/html; charset=utf-8");
require_once "./include/ini.php";
require_once "./include/session_ini.php";
require_once "./include/func.php";
require_once "./include/wx_user.php";
require_once "./include/club_user.php";
require_once "./include/dbo.php";

$action = isset($_GET['a']) ? $_GET['a'] : null;

//把用户信息写入到数据库
if($action == 'doReg')
{
    $phone = trim($_POST['resgiterPhone']);
    if(!is_phone($phone)) {
        jump("reg.php", "手机号码拼写错误！");
    }

    $dbo = new dbo();

    //检查手机号码是否已经注册
    $_exist_club_user_info = find_club_user_info_by_phone($phone, $dbo);
    if($_exist_club_user_info['phone'] == $phone) {
        jump("login.php", $phone."手机号码已注册，请登录！");
    }

    //检查手机短信验证码
    $resgiterCode = trim($_POST['resgiterCode']);
    //if(!is_sms_code($resgiterCode))
    //{
    //    jump("reg.php", "短信验证码错误！");
    //}

    //获取密码
    $pwd = $_POST['resgiterPassword'];
    if(empty($pwd) || strlen($pwd) < 6 || strlen($pwd) > 16) {
        jump("reg.php", "密码必须为6-16位字母或数字组合！");
    }
    $pwd = md5($pwd);
    $pwdsalt = get_pwd_salt(3);
    $pwd = md5($pwdsalt.$pwd);

    $reg_user_info = array(
        'user_role'      => '1',//用户角色,1会员
        'club_grade'     => '1',//会员等级，注册会员
        'user_real_name' => '',
        'openid'         => get_openid(),
        'wx_nickname'    => '',//昵称，通过微信获取
        'phone'          => $phone,
        'psalt'          => $pwdsalt,
        'pwd'            => $pwd,
        'sex'            => '',//性别，通过微信获取
        'reg_time'       => STIME,
        'last_login_time' => '',
        'login_count'    => 0,
        'balance'        => 0,
        'points'         => defined('POINT_REG') ? POINT_REG : 0,//注册是否送积分？送多少。
        'note'           => ''
    );

    $club_user_id = post_user_info_to_database($reg_user_info, $dbo);
    if($club_user_id) {
        //注册session
        $_exist_club_user_info = array(
            'openid' => $reg_user_info['openid'],
            'user_id' => $club_user_id
        );
        reg_session_club_user_info($_exist_club_user_info);
        jump("user_center.php");
    }
}

?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>会员注册</title>
    <style>
        ul{list-style: none;}
        ul li{margin:1em;}
    </style>
</head>
<body>
    <div style="text-align: center;font-size:3em;">
        <img src="./images/logo.jpg" />
        <div>
            <div style="text-align: right;"><a href="login.php">登录></a></div>
            <form action="reg.php?a=doReg" method="post">
                <ul>
                    <li>
                        <input type="number" placeholder="请输入手机号" id="resgiterPhone" name="resgiterPhone" style="font-size:1em;width:20em; height:3em;" />
                    </li>
                    <li>
                        <input type="password" placeholder="密码，6-16位字母或数字" id="resgiterPassword" name="resgiterPassword" style=" font-size:1em;width:20em; height:3em;" />
                    </li>
                    <li>
                        <input type="text" placeholder="验证码" id="resgiterCode" name="resgiterCode" style=" font-size:1em;width:10em; height:3em;" />
                        <span>获取验证码</span>
                    </li>
                </ul>
                <div style="margin:1em;">
                    <input type="radio" checked="checked" name="huiyunxuzhi" id="huiyunxuzhi" style="width:3em; height:3em;" />
                    <label >我已阅读了《会员须知》</label>
                </div>
                <button type="submit" id="but_register" style="width:10em; height:3em; font-size:1em;">注册</button>
            </form>
        </div>
    </div>
</body>
</html>