package cn.stkit.wxl.utils;

import cn.stkit.wxl.persistence.beans.User;

import javax.servlet.http.HttpSession;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file SessionUtil
 * @desc 用户会话工具类
 * @website http://www.stkit.cn/
 * @date 2020/6/17 10:01 上午
 */

public class SessionUtil
{
    /**
     * 通过openid判断用户是否登录
     * @param openid
     * @param session
     * @return true已登录，false未登录
     */
    public static boolean isUserLogin(String openid, HttpSession session)
    {
        if(session.getAttribute("open_id") == null) return false;

        if(session.getAttribute("open_id").equals(openid)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 通过UserId判断用户是否登录
     * @param userId
     * @param session
     * @return true已登录，false未登录
     */
    public static boolean isUserLoginByUserId(Integer userId, HttpSession session)
    {
        if(session.getAttribute("user_id") == null) return false;

        if(session.getAttribute("user_id").equals(userId)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 设置用户Session
     * @param user
     * @param session
     * @return
     */
    public static boolean regUserSession(User user, HttpSession session)
    {
        session.setAttribute("open_id", user.getOpenId());
        session.setAttribute("user_id", user.getUserId());
        session.setAttribute("user_name", user.getUserName());
        session.setAttribute("wechat_nickname", user.getWechatNickname());
        session.setAttribute("role_id", user.getRoleId());
        session.setAttribute("phone", user.getPhone());
        return true;
    }

    /**
     * 清除用户session
     * @param session
     * @return
     */
    public static boolean clearUserSession(HttpSession session)
    {
        session.removeAttribute("open_id");
        session.removeAttribute("user_id");
        session.removeAttribute("user_name");
        session.removeAttribute("wechat_nickname");
        session.removeAttribute("role_id");
        session.removeAttribute("phone");
        return true;
    }
}