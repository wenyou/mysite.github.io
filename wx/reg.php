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
/*require_once "./include/dbo.php";*/
require_once "./include/CMysqlPdo.php";

$action = isset($_GET['a']) ? $_GET['a'] : null;

//把用户信息写入到数据库
if($action == 'doReg')
{
    //检查姓名
    $real_name = trim($_POST['resgiterRealName']);
    if(!is_real_name($real_name,2,8)) {
        jump("reg.php", "姓名格式错误！");
    }

    //性别
    $sex = trim($_POST['resgiterSex']);
    if(!in_array($sex,['1','2'])) {
        jump("reg.php", "性别选择错误！");
    }

    $phone = trim($_POST['resgiterPhone']);
    if(!is_phone($phone)) {
        jump("reg.php", "手机号码拼写错误！");
    }
    $dbo = new CMysqlPdo($DB_CONFIG);
    //检查手机号码是否已经注册
    $_exist_club_user_info = find_club_user_info_by_phone($phone, $dbo);
    if($_exist_club_user_info['phone'] == $phone) {
        jump("login.php", $phone."手机号码已注册，请登录！");
    }

    //检查手机短信验证码
    $resgiterCode = trim($_POST['resgiterCode']);
    if(!is_sms_code($resgiterCode)) {
        jump("reg.php", "短信验证码错误！");
    }

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
        'club_card_num'  => create_club_user_card_num($phone),//会员卡卡号
        'user_real_name' => $real_name,
        'openid'         => get_openid(),
        'wx_nickname'    => 'apache',//昵称，通过微信获取
        'phone'          => $phone,
        'psalt'          => $pwdsalt,
        'pwd'            => $pwd,
        'sex'            => $sex,//性别，通过微信获取
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
}elseif($action == 'getCode'){
    if (isAjax()){
        $phone = trim($_POST['resgiterPhone']);
        if(!is_phone($phone)) {
            jump("reg.php", "手机号码拼写错误！");
        }
        $sms_code = random(6,1);//6位随机数字
        set_session('sms_code',$sms_code);
        send_sms_code($phone,$sms_code);
        echo json_encode($sms_code);exit;
    }else{
        jump("reg.php", "请求无效！");
    }
}

?>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
    <title>会员注册</title>
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
                <div class="ui-col ui-col current"><a href="javascript:void(0);">注册</a></div>
                <div class="ui-col ui-col"><a href="login.php">登录</a></div>
            </div>
        </div>
    </div>
    <div class="l-r-box">
        <div class="l-r-form">
            <div class="ui-form ui-border-t">
                <form action="reg.php?a=doReg" method="post" onsubmit="return check_reg();">
                    <div class="ui-form-item ui-form-item-r ui-border-b">
                        <input type="text" placeholder="姓名" id="resgiterRealName" name="resgiterRealName" />
                        <div class="select-sex ui-border-l">
                            <label class="ui-radio" for="radio1">
                                <input type="radio" name="resgiterSex" id="radio1" value="1" checked><span>男</span>
                            </label>
                            <label class="ui-radio" for="radio2">
                                <input type="radio" name="resgiterSex" id="radio2" value="2"><span>女</span>
                            </label>
                        </div>
                        <a href="javascript:void(0);" class="ui-icon-close"></a>
                    </div>
                    <div class="ui-form-item ui-form-item-pure ui-border-b">
                        <input type="number" placeholder="请输入手机号" id="resgiterPhone" name="resgiterPhone" />
                        <a href="javascript:void(0);" class="ui-icon-close"></a>
                    </div>
                    <div class="ui-form-item ui-form-item-pure ui-border-b">
                        <input type="password" placeholder="密码，6-16位字母或数字" id="resgiterPassword" name="resgiterPassword" />
                        <a href="javascript:void(0);" class="ui-icon-close"></a>
                    </div>
                    <div class="ui-form-item ui-form-item-r ui-border-b">
                        <input type="text" placeholder="验证码" id="resgiterCode" name="resgiterCode" />
                        <button type="button" class="ui-border-l" id="getcode">获取验证码</button><!-- 若按钮不可点击则添加 disabled 类 -->
                        <a href="javascript:void(0);" class="ui-icon-close"></a>
                    </div>
                    <div class="ui-btn-wrap">
                        <button type="submit" id="but_register" class="ui-btn-lg reg-btn">注册</button>
                    </div>
                </form>
            </div>
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
        function check_reg() {
            var real_name = $("#resgiterRealName").val();
            var phone = $("#resgiterPhone").val();
            var pwd = $("#resgiterPassword").val();
            if (!checkrealname(real_name,2,8)){
                tips('请输入有效的姓名！');
                return false;
            }
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
            if (!checkpwd(pwd,6,16)){
                tips('请输入有效的密码！');
                return false;
            }
        }
        var countdown,time = 60;
        $("#getcode").on('tap',function (e) {
            var _this = $(this);
            var phone = $("#resgiterPhone").val();
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
            $.ajax({
                type: 'POST',
                url: 'reg.php?a=getCode',
                dataType: 'json',
                data:{resgiterPhone:phone},
                success:function (data) {
                    alert(data);
                    _this.addClass("disabled");
                    _this.text(time+"s后重试");
                    countdown = window.setInterval("clock()",1000);
                }
            })
        });
        function clock() {
            time--;
            if(time>=0){
                $("#getcode").text(time+"s后重试");
            }else {
                $("#getcode").removeClass("disabled");
                $("#getcode").text("重新获取");
                window.clearInterval(countdown);
            }
        }

    </script>
</body>
</html>