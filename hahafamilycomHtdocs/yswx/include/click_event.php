<?php

/**
 * 用户菜单点击事件处理
 * User: youwenzhang
 * Date: 17/7/18
 * Time: 下午4:34
 */
class click_event
{
    function __construct($postObj, &$content, &$transmit_type)
    {
        switch ($postObj->EventKey) {
            case "menDian":
                $transmit_type = 'news';
                $this->men_dian($content);
                break;
            case "chongZhi":
                $transmit_type = 'news';
                $this->chong_zhi($content, $postObj->FromUserName);
                break;
            case "zhuCe":
                $nickname = $this->reg($postObj);
                $this->transmitResult($postObj->EventKey, $content, $postObj->FromUserName,$nickname);
                break;
            case "jiFen":
                $this->transmitResult($postObj->EventKey, $content,$postObj->FromUserName);
                break;
            default:
                $this->transmitResult($postObj->EventKey, $content,$postObj->FromUserName);
        }
    }

    /**
     * 门店查询
     */
    function men_dian(&$content, $openid)
    {
        $content[0] = array(
            'Title' => '到店路线',
            'Description' => "店面地址：合肥市蜀山区长江西路三里奄国购广场4楼\n联系电话：0551-45687545",
            'PicUrl' => 'http://www.hahafamily.com/yswx/images/peterclub-addess.jpg',
            'Url' => 'http://www.hahafamily.com/yswx/about.php?a=d&a=1&openid='.$openid
        );
    }

    /**
     * 充值
     * @param $content
     * @param $openid
     */
    function chong_zhi(&$content, $openid)
    {
        $content[0] = array(
            'Title' => '充值',
            'Description' => "充值8折优惠，并送好礼，详情点击查看 \n联系电话：0551-45687545",
            'PicUrl' => 'http://www.hahafamily.com/yswx/images/peterclub-addess.jpg',
            'Url' => 'http://www.hahafamily.com/yswx/topup.php?a=up&openid='.$openid
        );
    }

    /**
     * 用户注册
     * @param $postObj
     * @return mixe
     */
    function reg($postObj)
    {
        $access_token = get_access_token();
        $openid = $postObj->FromUserName;
        //获取用户信息
        $user_url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=".$access_token."&openid=".$openid."&lang=zh_CN";
        $result = file_get_contents($user_url);
        $user_info = json_decode($result);
        $nickname = $user_info->nickname;
        return $nickname;

    }


    function transmitResult($text, &$content,$openid,$nickname="")
    {
        $content = "你点击了:".$text."\n你的OpenID是 ".$openid."\n".$nickname;
    }


}