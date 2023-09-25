<?php
/**
 * 微信API回调接口
 * User: youwenzhang
 * Date: 17/7/18
 * Time: 下午2:41
 */

class wechat_callback_api
{
    public function valid()
    {
        $echoStr = $_GET["echostr"];
        if($this->checkSignature()){
            echo $echoStr;
            exit;
        }
    }

    private function checkSignature()
    {
        $signature = $_GET["signature"];
        $timestamp = $_GET["timestamp"];
        $nonce = $_GET["nonce"];
        $token = TOKEN;
        $tmpArr = array($token, $timestamp, $nonce);
        sort($tmpArr);
        $tmpStr = implode($tmpArr);
        $tmpStr = sha1($tmpStr);

        if($tmpStr == $signature){
            return true;
        }else{
            return false;
        }
    }

    public function responseMsg()
    {
        $postStr = $GLOBALS["HTTP_RAW_POST_DATA"];
        //$postStr = file_get_contents("php://input");
        if (!empty($postStr)){
            $this->logger("R ".$postStr);
            $postObj = simplexml_load_string($postStr, 'SimpleXMLElement', LIBXML_NOCDATA);
            $RX_TYPE = trim($postObj->MsgType);

            switch ($RX_TYPE)
            {
                case "event":
                    $result = $this->receiveEvent($postObj);
                    break;
                case "text":
                    $result = $this->receiveText($postObj);
                    break;
            }
            $this->logger("T ".$result);
            echo $result;
        }else {
            echo "";
            exit;
        }
    }

    private function receiveEvent(&$postObj)
    {
        $content = "";
        $transmit_type = 'text';
        switch ($postObj->Event)
        {
            case "subscribe":
                $this->save_wx_user_info($postObj);
                $content = "欢迎关注Peter Club！";
                break;
            case "unsubscribe":
                $content = "取消关注";
                break;
            case "CLICK":
                $click_event_obj = new click_event($postObj, $content, $transmit_type);
                break;
        }

        if($transmit_type == 'news') {
            $result = $this->transmitNews($postObj, $content);
        } else {
            $result = $this->transmitText($postObj, $content);
        }

        return $result;
    }

    private function save_wx_user_info($postObj)
    {
        $openid = $postObj->FromUserName;
        file_put_contents(APP_PATH.'data/'.time().'-'.$openid.'.txt',$openid);
    }

    //接收文本消息
    private function receiveText(&$postObj)
    {
        $keyword = trim($postObj->Content);
        $content = date("Y-m-d H:i:s",time())."\n欢迎关注Peter Club!\n请使用下面的菜单";

        if(is_array($content)){
            if (isset($content[0]['PicUrl'])){
                $result = $this->transmitNews($postObj, $content);
            }else if (isset($content['MusicUrl'])){
                $result = $this->transmitMusic($postObj, $content);
            }
        }else{
            $result = $this->transmitText($postObj, $content);
        }

        return $result;
    }


    private function transmitText($postObj, $content)
    {
        $textTpl = "<xml>
<ToUserName><![CDATA[%s]]></ToUserName>
<FromUserName><![CDATA[%s]]></FromUserName>
<CreateTime>%s</CreateTime>
<MsgType><![CDATA[text]]></MsgType>
<Content><![CDATA[%s]]></Content>
</xml>";
        $result = sprintf($textTpl, $postObj->FromUserName, $postObj->ToUserName, time(), $content);
        return $result;
    }

    private function transmitNews($postObj, $arr_item)
    {
        if(!is_array($arr_item))
            return;

        $itemTpl = "    <item>
        <Title><![CDATA[%s]]></Title>
        <Description><![CDATA[%s]]></Description>
        <PicUrl><![CDATA[%s]]></PicUrl>
        <Url><![CDATA[%s]]></Url>
    </item>
";
        $item_str = "";
        foreach ($arr_item as $item)
            $item_str .= sprintf($itemTpl, $item['Title'], $item['Description'], $item['PicUrl'], $item['Url']);

        $newsTpl = "<xml>
<ToUserName><![CDATA[%s]]></ToUserName>
<FromUserName><![CDATA[%s]]></FromUserName>
<CreateTime>%s</CreateTime>
<MsgType><![CDATA[news]]></MsgType>
<Content><![CDATA[]]></Content>
<ArticleCount>%s</ArticleCount>
<Articles>
$item_str</Articles>
</xml>";

        $result = sprintf($newsTpl, $postObj->FromUserName, $postObj->ToUserName, time(), count($arr_item));
        return $result;
    }

    private function transmitMusic($postObj, $musicArray)
    {
        $itemTpl = "<Music>
    <Title><![CDATA[%s]]></Title>
    <Description><![CDATA[%s]]></Description>
    <MusicUrl><![CDATA[%s]]></MusicUrl>
    <HQMusicUrl><![CDATA[%s]]></HQMusicUrl>
</Music>";

        $item_str = sprintf($itemTpl, $musicArray['Title'], $musicArray['Description'], $musicArray['MusicUrl'], $musicArray['HQMusicUrl']);

        $textTpl = "<xml>
<ToUserName><![CDATA[%s]]></ToUserName>
<FromUserName><![CDATA[%s]]></FromUserName>
<CreateTime>%s</CreateTime>
<MsgType><![CDATA[music]]></MsgType>
$item_str
</xml>";

        $result = sprintf($textTpl, $postObj->FromUserName, $postObj->ToUserName, time());
        return $result;
    }

    private function logger($log_content)
    {
        if(isset($_SERVER['HTTP_APPNAME'])){   //SAE
            sae_set_display_errors(false);
            sae_debug($log_content);
            sae_set_display_errors(true);
        }else if($_SERVER['REMOTE_ADDR'] != "127.0.0.1"){ //LOCAL
            $max_size = 10000;
            $log_filename = "log.xml";
            if(file_exists($log_filename) and (abs(filesize($log_filename)) > $max_size)){unlink($log_filename);}
            file_put_contents($log_filename, date('H:i:s')." ".$log_content."\r\n", FILE_APPEND);
        }
    }
} 