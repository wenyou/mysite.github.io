<?php
/**
 * 俱乐部会员信息
 * User: youwenzhang
 * Date: 17/7/20
 * Time: 上午10:28
 */

//获取session用户信息
function get_session_user_info($openid)
{
    $_session_user_info = get_session_info();
    /*if($openid != $_session_user_info['openid']) {
        $_session_user_info = array();
    }*/
    return $_session_user_info;
}

/**
 * 注册session信息
 * @param $_exist_club_user_info
 */
function reg_session_club_user_info(&$_exist_club_user_info)
{
    set_session_info(array(
        'openid'=>$_exist_club_user_info['openid'],
        'user_id'=>$_exist_club_user_info['user_id']
    ));
}

/**
 * 用户是否登陆？
 * @param $_session_club_user_info
 * @param $club_user_info
 * @param $dbo
 */
function is_logined(&$_session_club_user_info, &$club_user_info, &$dbo)
{
    if($_session_club_user_info['openid'] && intval($_session_club_user_info['user_id']) != 0) {
        $club_user_info = $dbo->findOne("club_users","user_id = {$_session_club_user_info['user_id']}");
        if(empty($club_user_info)) {
            jump("reg.php", "会员已被注销或删除，请重新注册！");
        }
    } else {
        jump("login.php");
        exit();
    }
}

/**
 * 添加新用户到用户表中
 * @param $reg_user_info
 * @param $dbo
 * @return mixed
 */
function post_user_info_to_database(&$reg_user_info,&$dbo)
{
    $dbo->beginTransaction();//开启事务处理
    $user_id = $dbo->insert("club_users", $reg_user_info);
    $user_id = intval($user_id);
    if($user_id) {
        //初始化余额表
        $balance_data = array(
            'user_id' => $user_id,
            'openid'  => $reg_user_info['openid'],
            'phone'   => $reg_user_info['phone'],
            'balance' => $reg_user_info['balance'],
            'last_topup_time' => 0,
            'last_topup_balance' => 0,
            'last_topup_type' => '',
            'topup_count' => 0,
            'last_pay_time' => 0,
            'last_pay_balance' => 0,
            'last_pay_type' => '',
            'pay_count' => 0,
            'note'=> ''
        );
        $balance_id = $dbo->insert("club_user_balance", $balance_data);
        if ($balance_id){
            //初始化积份表
            global $POINT_TYPE;
            $point_id = $dbo->insert("club_user_accumulate_points", $point_data = array(
                'user_id' => $user_id,
                'openid'  => $reg_user_info['openid'],
                'phone'   => $reg_user_info['phone'],
                'points' => $reg_user_info['points'],
                'last_add_time' => $reg_user_info['points'] > 0 ? STIME : 0,//是否注册时送积分
                'last_add_points' => $reg_user_info['points'] > 0 ? $reg_user_info['points'] : 0,
                'last_add_type' => $reg_user_info['points'] > 0 ? $POINT_TYPE['reg'] : '',
                'add_count' => $reg_user_info['points'] > 0 ? 1 : 0,
                'last_deduct_time' => 0,
                'last_deduct_points' => 0,
                'last_deduct_type' => '',
                'deduct_count' => 0
            ));
            $point_id = intval($point_id);
            if ($point_id){
                //是否注册送积分
                if($reg_user_info['points'] > 0) {
                    $point_log_data = array(
                        'point_id' => $point_id,
                        'user_id'  => $user_id,
                        'openid'   => $reg_user_info['openid'],
                        'change_points' => $reg_user_info['points'],
                        'change_time'   => STIME,
                        'add_or_deduct' => '1', //增加
                        'change_type'   => $POINT_TYPE['reg'],//注册
                        'change_note'   => ''
                    );
                    $point_log_id = $dbo->insert("club_user_accumulate_points_logs", $point_log_data);
                    if (false == $point_log_id){
                        $dbo->rollBack();//事务回滚
                    }
                }
                //初始化会员等级表
                $user_grade_log = array(
                    'user_id' => $user_id,
                    'club_grade' => $reg_user_info['club_grade'],
                    'pre_club_grade' => 0,
                    'up_or_down' => 1,
                    'change_type' => 1,//注册
                    'change_note' => '',
                    'opt_user_id' => 0,
                    'opt_user_name' => '系统',
                    'change_time' => STIME
                );
                $grade_log_id = $dbo->insert("club_user_grade_logs", $user_grade_log);
                if ($grade_log_id){
                    $dbo->commit();//事务提交
                }else{
                    $dbo->rollBack();//事务回滚
                }
            }else{
                $dbo->rollBack();//事务回滚
            }
        }else{
            $dbo->rollBack();//事务回滚
        }
    }
    return $user_id;
}


function find_club_user_info_by_phone($phone, &$dbo)
{
    $user_info = $dbo->findOne("club_users","phone = '{$phone}'");
    return $user_info;
}

/**
 * 生产会员卡或者从外部系统中导入会员卡
 * @param null $phone
 * @return string
 */
function create_club_user_card_num($phone = null)
{
    if(empty($phone))
    {
        return '';
    } else {
        //生产会员卡卡号
        $card_num = 'NO.'.$phone;
        return $card_num;
    }
}


