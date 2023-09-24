<?php
/**
 * 初始化常量，对不同的公众号进行部署，请修改相关的配置值。
 * User: youwenzhang
 * Date: 17/7/18
 * Time: 下午2:39
 */

/**
 * 公众号配置项
 */
//公共号TOKEN
define("TOKEN", "you_wen_zhang_shushan_hefei");
//公共号APP ID
define("APPID", "wxc0644e50d59425ac");
//开发者密码(AppSecret)
define("APPSECRET", "fc9073e8e3b889d652338425bcf720e2");
//消息加解密密钥(EncodingAESKey)
define("ENAESKEY", "VH2SRfG5eN5DD00yKyQwdaru4VnqkQet5s1B2Vr72qa");

/**
 * 应用程序定义
 */
//应用程序根据目录，不要改动
define("APP_PATH", realpath(dirname(__FILE__).'/../').'/');
define("APP_URL", "http://www.hahafamily.com/wx/");
//先在php.ini里设置好时区，设置成为'PRC'
define("STIME", time());
//数据库配置
//mysql database address
define('DB_HOST','bdm27424655.my3w.com');
//mysql database user
define('DB_USER','bdm27424655');
//database password
define('DB_PASSWD','20H20haYan06zhW03');
//database name
define('DB_NAME','bdm27424655_db');
//mysql connent type
define('MYSQLCONN','mysqli');
/**
 * 激励措施
 */
//注册送积分
define('POINT_REG', 10);
//每日签到送积分
define('POINT_SIGN', 5);

/**
 * 枚举类型配置定义
 */
//积分方式
$POINT_TYPE = array(
    'reg' => 1,//注册会员激励
    'sign' => 2,//每日签到激励
    'game' => 3,//游戏积分，每局游戏的积分
    'deduct' => 4,//消费抵扣
    'ex_props' => 5,//换取道具
    'ex_gift' => 6,//换取礼品
);
//用户角色
$USER_ROLES = array(
    '1' => '会员',
    '2' => '管理员'
);
//会员等级
$CLUB_GRADE = array(
    '1' => '注册会员',
    '2' => '铜牌会员',
    '3' => '银牌会员',
    '4' => '金牌会员',
    '5' => '钻石会员'
);
//充值金额对应会员等级
$MONEY_GRADE = array(
    '1000' => 1,//<2000元，1级会员
    '2000' => 2,//2000元, 2级会员
    '3000' => 3,//3000元, 3级会员
    '4000' => 4,//4000元, 4级会员
);
//会员等级优惠比例
$GRADE_DISCOUNT = array(
    '1' => '0',//不优惠
    '2' => '0.95',//95%，9.5折
    '3' => '0.85',//85%，5.5折
    '4' => '0.75',//75%，7.5折
);