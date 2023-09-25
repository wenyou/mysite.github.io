<?php
/**
 * 生成菜单
 * User: youwenzhang
 * Date: 17/7/18
 * Time: 下午2:39
 */
header('Content-Type: text/html; charset=UTF-8');
require_once "./include/ini.php";
require_once "./include/get_access_token.php";

//获取access_token
$access_token = get_access_token();

//自定义菜单
$data = '{
		 "button":[
		 {
			   "name":"会员",
			   "sub_button":[
				{
				   "type":"click",
				   "name":"充值",
				   "key":"chongZhi"
				},
				{
				   "type":"click",
				   "name":"积分",
				   "key":"jiFen"
				},
				{
				   "type":"view",
				   "name":"好友",
				   "url": '.APP_URL.'"user_center.php?a=friend"
				},
				{
				   "type":"view",
				   "name":"个人中心",
				   "url":'.APP_URL.'"user_center.php"
				}]
		  },
		  {
			   "name":"订餐",
			   "sub_button":[
				{
				   "type":"view",
				   "name":"订餐",
				   "url":'.APP_URL.'"meal.php"
				},
				{
				   "type":"click",
				   "name":"门店",
				   "key":"menDian"
				},
				{
				   "type":"click",
				   "name":"我的订单",
				   "key":"woDeDingDan"
				},
				{
				   "type":"click",
				   "name":"客服",
				   "key":"keFu"
				}]
		   },
		   {
			   "name":"赛事",
			   "sub_button":[
			   {
			        "type":"click",
			        "name":"排名",
			        "key":"paiMing"
			   },
			   {
                    "type":"click",
                    "name":"报名",
                    "key":"baoMing"
			   },
			   {
                    "type":"view",
                    "name":"更多",
                    "url":'.APP_URL.'"more.php"
			   }]
		   }]
       }';

//创建菜单URL
$menu_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=".$access_token;
var_dump($access_token);


$ch = curl_init($menu_url);
curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "POST");
curl_setopt($ch, CURLOPT_POSTFIELDS, $data);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json', 'Content-Length: ' . strlen($data)));
$info = curl_exec($ch);
$menu = json_decode($info);
print_r($info);
echo "<br /><br />";
if($menu->errcode == "0"){
    echo "菜单创建成功!";
}else{
    echo "菜单创建失败!";
}

echo "<br /><br />".date("Y-m-d H:i:s",time());