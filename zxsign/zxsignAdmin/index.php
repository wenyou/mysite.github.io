<?php
/**
 * zxsign admin api
 * author: Zeeny
 * date: 2022-9-2
 */
error_reporting(E_ALL);
//根路径
$_ROOT_PATH = dirname(__FILE__);
define('IN_STK', true);
//路径分隔符
define('PS', PATH_SEPARATOR);
define('DS', DIRECTORY_SEPARATOR);
define('ROOT_PATH', $_ROOT_PATH.DS);
//环境常量
define('IS_CLI', PHP_SAPI == 'cli' ? true : false);
define('REQUEST_METHOD',$_SERVER['REQUEST_METHOD']);
//导入函数库
require_once ROOT_PATH.'common'.DS.'funs.php';

//获取请求参数
$q = request('_q');
//模块文件
$model_array = array(
    'zxemail',
);
if(in_array($q, $model_array)){
    require_once ROOT_PATH.'controller'.DS.$q.'Controller.php';
    $_controller = $q.'Controller';
    $_controller = new $_controller();
    $_action = request('_a');
    if($_action){
        $_action = $_action.'Action';
    }else{
        $_action = null;
    }
    if($_action && method_exists($_controller,$_action)){
       $_controller->$_action();
    }else{
        die('The '.$_action.' method in the '.$q.'Controller class does not exist!');
    }
}else {
    die('exit');
}