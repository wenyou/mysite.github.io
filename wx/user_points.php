<?php
/**
 * 会员积分
 * User: youwenzhang
 * Date: 17/7/31
 * Time: 上午11:15
 */


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