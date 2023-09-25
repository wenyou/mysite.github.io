<?php
/**
 * 设置session
 * User: youwenzhang
 * Date: 17/7/20
 * Time: 上午10:32
 */
session_save_path(APP_PATH.'data');
session_start();

function get_session_info()
{
    return $_SESSION;
}

function get_session_value_by_key($key)
{
    if(isset($_SESSION[$key]))
        return $_SESSION[$key];
    return '';
}

function set_session_info($user_info_arr)
{
    $_SESSION['openid'] = $user_info_arr['openid'];
    $_SESSION['user_id'] = $user_info_arr['user_id'];
}

function del_session_info()
{
    $_SESSION['openid'] = null;
    $_SESSION['user_id'] = null;
    unset($_SESSION['openid']);
    unset($_SESSION['user_id']);
    unset($_SESSION);
    session_destroy();
}