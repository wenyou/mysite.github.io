<?php
/**
 * 用户中心
 * User: youwenzhang
 * Date: 17/7/19
 * Time: 下午5:46
 */
header("Content-type: text/html; charset=utf-8");

require_once "./include/ini.php";
require_once "./include/get_access_token.php";
require_once "./include/session_ini.php";
require_once "./include/wx_user.php";
require_once "./include/club_user.php";
require_once "./include/dbo.php";
require_once "./include/func.php";

//echo "<h1>个人中心页面，这里怎么拿到用户的openid</h1>";

$action = isset($_GET['a']) ? $_GET['a'] : null;
$dbo  = new dbo();

//取得公众号关注者信息
$openid = "";
$wx_user_info = get_wx_user_info($openid);
$wx_nickname = $wx_user_info->nickname;

//已注册成为俱乐部会员的用户session信息
$_session_club_user_info = get_session_user_info($openid);
//已注册成为俱乐部会员的用户信息
$club_user_info = array();
is_logined($_session_club_user_info,$club_user_info,$dbo);

/**
 * 积分页
 */
if($action == 'jifen')
{

}
else
{
    /**
     * 会员中心默认页
     */

}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Peter Club会员</title>
    <style>
        ul{list-style: none;}
        ul li{margin:1em;}
    </style>
</head>
<body>
<div style="text-align: center;font-size:3em;">
    <img src="./images/logo.jpg" />
    <h1><?php echo $club_user_info['phone'];?></h1>
    <h2><a href="./user_points.php">我的积分 <?php echo $club_user_info['points'];?> ></a></h2>
    <div>
        <table>
            <tr><td width="34%" style="font-size: 2em; text-align: center">500</td><td width="34%" style="font-size: 2em; text-align: center">20</td></tr>
            <tr><td style="text-align: center">累计面杀</td><td style="text-align: center">排名</td></tr>
        </table>
    </div>
    <h2>我的钱包  <?php echo $club_user_info['balance'];?></h2>
    <h2>我的订单  5</h2>
    <h2>我的消息</h2>
    <h2>邀请好友</h2>
    <h2>设置</h2>
</div>
</body>
</html>