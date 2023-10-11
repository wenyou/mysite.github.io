<?php
/**
 * 函数库
 * author: Zeeny
 * date: 2022-9-2
 */
if (!defined('IN_STK')) exit('Access Denied');
/**
 * 获取请求参数
 * @param $key
 * @return mixed|null
 */
function request($key = null) {
    $request = null;
    foreach(array($_POST, $_GET) as $_request) {
        if($_request && is_array($_request)){
            foreach($_request as $_key => $_value) {
                $request[$_key] = $_value;
            }
        }
    }

    if($key && isset($request[$key])){
        $request = $request[$key];
    }else if($key) {
        $request = null;
    }
    return $request;
}

/**
 * 浏览器友好的变量输出
 * @access public
 * @param  mixed       $var   变量
 * @param  boolean     $echo  是否输出(默认为 true，为 false 则返回输出字符串)
 * @param  string|null $label 标签(默认为空)
 * @param  integer     $flags htmlspecialchars 的标志
 * @return null|string
 */
function dump($var, $echo = true, $label = null, $flags = ENT_SUBSTITUTE) {
    $label = (null === $label) ? '' : rtrim($label) . ':';

    ob_start();
    var_dump($var);
    $output = preg_replace('/\]\=\>\n(\s+)/m', '] => ', ob_get_clean());

    if (IS_CLI) {
        $output = PHP_EOL . $label . $output . PHP_EOL;
    } else {
        if (!extension_loaded('xdebug')) {
            $output = htmlspecialchars($output, $flags);
        }

        $output = '<pre>' . $label . $output . '</pre>';
    }

    if ($echo) {
        echo($output);
        return;
    }

    return $output;
}