<?php
/**
 * 共用函数
 * User: youwenzhang
 * Date: 17/7/20
 * Time: 下午5:15
 */
/**
 * 是否是手机号码
 * @param $phone
 * @return bool
 */
function is_phone($phone)
{
    if (!is_numeric($phone)) {
        return false;
    }
    return preg_match('#^13[\d]{9}$|^14[5,7]{1}\d{8}$|^15[^4]{1}\d{8}$|^17[0,6,7,8]{1}\d{8}$|^18[\d]{9}$#', $phone) ? true : false;
}

function jump($uri, $msg = '')
{
    if(empty($msg)) {
        header("Location: ".APP_URL.$uri);
    }
    else
    {
        echo "<!DOCTYPE html><html lang=\"en\"><head><meta http-equiv=\"refresh\" content=\"1;url=".APP_URL.$uri."\"></head><body><h1 style='text-align: center; font-size: 2em; margin-top: 10em;'>".$msg."</h1></body></html>";
    }
    exit();
}

/**
 * 取得$len长度的随机字符串
 * @param $len
 * @return string
 */
function get_pwd_salt($len)
{
    $chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    mt_srand((double)microtime() * 1000000);
    $salt = "";
    while(strlen($salt) < $len)
        $salt .= substr($chars,(mt_rand() % strlen($chars)),1);
    return $salt;
}

/**
 * 检查验证码
 * @param $code 等待验证的验证码
 * @param string $sms_code 系统生产的已经发送给用户的验证码
 * @return bool
 */
function is_sms_code($code, $sms_code='')
{
    //得到短信验证码
    $sms_code = get_session_value_by_key('sms_code');

    if(empty($code) || empty($sms_code)) {
        return false;
    }
    if($sms_code == $code)
    {
        return true;
    }
    return false;
}

/**
 * 显示系统信息
 *
 * @param string $msg 信息
 * @param string $url 返回地址
 * @param boolean $isAutoGo 是否自动返回 true false
 */
function xMsg($msg, $url = 'javascript:history.back(-1);', $isAutoGo = false)
{
    if ($msg == '404') {
        header("HTTP/1.1 404 Not Found");
        $msg = '抱歉，你所请求的页面不存在！';
    }
    echo <<<EOT
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN">
<head>
EOT;
    if ($isAutoGo) {
        echo "<meta http-equiv=\"refresh\" content=\"2;url=$url\" />";
    }
    echo <<<EOT
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>提示信息</title>
<style type="text/css">
<!--
body {
	background-color:#F7F7F7;
	font-family: Arial;
	font-size: 12px;
	line-height:150%;
}
.main {
	background-color:#FFFFFF;
	font-size: 12px;
	color: #666666;
	width:650px;
	margin:60px auto 0px;
	border-radius: 10px;
	padding:30px 10px;
	list-style:none;
	border:#DFDFDF 1px solid;
}
.main p {
	line-height: 18px;
	margin: 5px 20px;
}
-->
</style>
</head>
<body>
<div class="main">
<p>$msg</p>
EOT;
    if ($url != 'none') {
        echo '<p><a href="' . $url . '">&laquo;点击返回</a></p>';
    }
    echo <<<EOT
</div>
</body>
</html>
EOT;
    exit;
}