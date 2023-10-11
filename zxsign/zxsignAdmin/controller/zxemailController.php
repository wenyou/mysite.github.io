<?php
/**
 * 中新电子签名后台操作控制器
 */
if (!defined('IN_STK')) exit('Access Denied');
class zxemailController
{
    public function getContentAction() {
        $arr = array(
            'uid' => 444444,
            'content' => '人生长恨水长东'
        );
        echo json_encode($arr);
    }
}