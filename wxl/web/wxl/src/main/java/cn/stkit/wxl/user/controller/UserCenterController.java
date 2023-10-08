package cn.stkit.wxl.user.controller;

import cn.stkit.wxl.model.WeChatUser;
import cn.stkit.wxl.object.ResponseVO;
import cn.stkit.wxl.persistence.beans.User;
import cn.stkit.wxl.persistence.mapper.UserMapper;
import cn.stkit.wxl.persistence.mapper.WeChatUserMpper;
import cn.stkit.wxl.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file UserCenterController
 * @desc 用户中心页面
 * @website http://www.stkit.cn/
 * @date 2020/6/16 7:56 下午
 */

@Controller
@RequestMapping(value = "/wxl/uc")
public class UserCenterController
{
    private static final Logger logger = LoggerFactory.getLogger(UserCenterController.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Resource
    private UserMapper userMapper;

    @Autowired(required = false)
    private WeChatUserMpper weChatUserMpper;

    /*@Resource
    private UserMapper userMapper;*/

    @GetMapping("")
    public ModelAndView index(Model model, HttpServletRequest request, HttpSession session)
    {
        System.out.println("----进入用户中心页面-----");
        WeChatUser weChatUser = null;
        if(request.getParameter("login") == null) {
            //从微信app(oauth2网页授权)过来，从微信接口获取weChatUser用户信息
            //获取微信用户信息
            weChatUser = WeChatUtil.getWeChatUser(request.getParameter("code"));
        } else {
            //从用户登陆页过来，从数据表获取weChatUser信息
            if(session.getAttribute("open_id") != null)
                weChatUser = weChatUserMpper.findWeChatUserByOpenid(session.getAttribute("open_id").toString());
        }
        if(weChatUser == null)
            weChatUser = new WeChatUser();

        System.out.println("获取微信用户信息: weChatUser = " + weChatUser);

        //判断用户是否登录
        if(ValidateUtil.isEmptyString(weChatUser.getOpenid())) {//没有微信用户信息
            System.out.println("==没有微信用户信息==");
            if(!SessionUtil.isUserLoginByUserId((Integer)session.getAttribute("user_id"), session)) { //通过用户userId来判断用户是否登录
                session.setAttribute("tmp_wechatuser", weChatUser);
                return ModelAndViewUtil.redirect("/wxl/uc/login");
            }
        } else { //有微信用户信息
            System.out.println("==有微信用户信息==");
            if(!(!ValidateUtil.isEmptyString(weChatUser.getOpenid()) && SessionUtil.isUserLogin(weChatUser.getOpenid(), session))) {
                session.setAttribute("tmp_wechatuser", weChatUser);
                System.out.println("==有微信用户信息，没有登录，跳转到登录==");
                return ModelAndViewUtil.redirect("/wxl/uc/login");
            }
        }
        int user_id = (Integer)session.getAttribute("user_id");
        User user = userMapper.findUserByUserId(user_id);
        if(user == null) {
            session.setAttribute("tmp_wechatuser", weChatUser);
            return ModelAndViewUtil.redirect("/wxl/uc/login");
        }

        if(ValidateUtil.isEmptyString(user.getNickName()) && !ValidateUtil.isEmptyString(weChatUser.getNickname())) {
            user.setNickName(weChatUser.getNickname());
        }
        if(ValidateUtil.isEmptyString(weChatUser.getHeadimgurl())) {
            weChatUser.setHeadimgurl("/assets/images/user.png");
        }

        model.addAttribute("title", "个人中心");
        model.addAttribute("user", user);
        model.addAttribute("weChatUser", weChatUser);
        return ModelAndViewUtil.view("user/index");
    }

    @PostMapping("/reg")
    @ResponseBody
    public ResponseVO reg(@RequestParam Map<String, Object> params, HttpSession session)
    {
        System.out.println("====执行注册操作======");

        System.out.println(params);
        String phone = params.get("phone").toString();
        String userPassword = (String) params.get("userPassword");
        String openid = params.get("openid").toString();
        String nickName = params.get("nickName").toString();

        userPassword = Md5Util.Md5Str(userPassword, phone.substring(7));
        int timestamp = TimeUtil.getNowTimestamp();

        WeChatUser weChatUser = null;
        if(session.getAttribute("tmp_wechatuser") == null) {
            System.out.println("====session_wechatuser empty===");
            weChatUser = new WeChatUser();
        } else {
            weChatUser = (WeChatUser)session.getAttribute("tmp_wechatuser");
        }
        System.out.println("session wechat user = " + weChatUser);

        //查询手机用户是否存在
        User user = userMapper.findUserByPhone(phone);
        System.out.println("db user = " + user);

        //一、手机号已经被注册，处理
        if(user != null) {
            if(!ValidateUtil.isEmptyString(openid) && !ValidateUtil.isEmptyString(user.getOpenId())){
                if(openid != user.getOpenId()) { //手机号码已经注册，但是不是当前微信号
                    return ModelAndViewUtil.error("这个手机号码已经与其他微信号绑定过了，不能重复注册，请更换号码注册或者进行尝试登录，或者与我们联系。");
                }
            } else if(!ValidateUtil.isEmptyString(openid) && ValidateUtil.isEmptyString(user.getOpenId())) {
                //当前有openid,但是已经注册的手机号没有openid, 则进行绑定操作
                //更新当前openid到user表中
                user.setOpenId(openid);
                user.setWechatNickname(weChatUser.getNickname());
                if(user.getSex() == 0) {
                    user.setSex(weChatUser.getSex());
                }
                user.setUpdatedTime(timestamp);
                user.setUpdatedUserId(user.getUserId());
                int resultUpdate = userMapper.updateUserOpenIDByUserId(user);
                System.out.println("resultUpdate = "+resultUpdate);

                //查询wechat_user表，进行微信用户信息的保存
                WeChatUser weChatUser1 = weChatUserMpper.findWeChatUserByOpenid(openid);
                if(weChatUser1 == null) {
                    weChatUser.setUserId(user.getUserId());
                    weChatUser.setCreatedTime(timestamp);
                    weChatUser.setCreatedUserId(user.getUserId());
                    int addWeChatUserResult = weChatUserMpper.addWebChatUser(weChatUser);
                    System.out.println("addWeChatUserResult = " + addWeChatUserResult);
                } else {
                    weChatUser.setUpdatedUserId(user.getUserId());
                    weChatUser.setUpdatedTime(timestamp);
                    int updateWeChatUserResult = weChatUserMpper.updateWebChatUser(weChatUser);
                    System.out.println("updateWeChatUserResult = " + updateWeChatUserResult);
                }

                if(resultUpdate > 0) {
                    return ModelAndViewUtil.success("注册成功：手机号码与微信号绑定成功！请登录。", "/wxl/uc/?login=1");
                } else {
                    return ModelAndViewUtil.error("注册失败：手机号码与微信号绑定出错！");
                }
            }

            return ModelAndViewUtil.error("这个手机号码已经注册过了，请登录，不要重复注册，谢谢！");
        }
        //二、手机号没有被注册，进行注册
        //1.首先如果有openid，则判断这个openid是否已经注册
        if(!ValidateUtil.isEmptyString(openid)) {
            user = userMapper.findUserByOpenId(openid);
            System.out.println("openid db user = " + user);
            if(user != null && !ValidateUtil.isEmptyString(user.getPhone())) { //openid已注册，并且有手机号
                return ModelAndViewUtil.error("您的微信号已经被手机号（"+user.getPhone()+"）注册过了，请确认该手机号是否为本人所有，如果是本人所有请登录，不要重复注册；如果非本人所有，请与我们联系！");
            } else if(user != null) { //openid已注册，但没有手机号
                //更新手机号到user数据表
                user.setNickName(nickName);
                user.setPhone(phone);
                user.setUserPassword(userPassword);
                user.setUpdatedUserId(user.getUserId());
                user.setUpdatedTime(timestamp);
                int updateUserPhoneResult = userMapper.updateUserPhoneByUserId(user);
                System.out.println("updateUserPhoneResult = " + updateUserPhoneResult);

                //查询wechat_user表，进行微信用户信息的保存
                WeChatUser weChatUser2 = weChatUserMpper.findWeChatUserByOpenid(openid);
                if(weChatUser2 == null) {
                    weChatUser.setUserId(user.getUserId());
                    weChatUser.setCreatedTime(timestamp);
                    weChatUser.setCreatedUserId(user.getUserId());
                    int addWeChatUserResult = weChatUserMpper.addWebChatUser(weChatUser);
                    System.out.println("addWeChatUserResult = " + addWeChatUserResult);
                } else {
                    weChatUser.setUpdatedUserId(user.getUserId());
                    weChatUser.setUpdatedTime(timestamp);
                    int updateWeChatUserResult = weChatUserMpper.updateWebChatUser(weChatUser);
                    System.out.println("updateWeChatUserResult = " + updateWeChatUserResult);
                }
                if(updateUserPhoneResult > 0) {
                    return ModelAndViewUtil.success("注册成功：微信号与手机号码绑定成功！请登录。", "/wxl/uc/?login=1");
                } else {
                    return ModelAndViewUtil.error("注册失败：微信号与手机号码绑定出错！");
                }
            }
            //user = null, openid未注册，进行注册
            user = new User();
            user.setUserName("");
            user.setNickName(nickName);
            user.setRealName("");
            user.setWechatNickname(weChatUser.getNickname());
            user.setOpenId(openid);
            user.setSex(weChatUser.getSex());
            user.setCreatedTime(timestamp);
            user.setCreatedUserId(0);
            user.setRoleId(1);
            user.setPhone(phone);
            user.setUserPassword(userPassword);
            user.setUserStatus((byte)1);
            int addUserResult = userMapper.addUser(user);
            System.out.println("new user addUserResult = " + addUserResult);

            if(addUserResult > 0) {
                user = userMapper.findUserByOpenId(openid);
                if(null != user) {
                    weChatUser.setUserId(user.getUserId());
                    weChatUser.setCreatedTime(timestamp);
                    weChatUser.setCreatedUserId(user.getUserId());
                    int addWeChatUserResult = weChatUserMpper.addWebChatUser(weChatUser);
                    System.out.println("new user addWeChatUserResult = " + addWeChatUserResult);
                }
                return ModelAndViewUtil.success("注册成功了，请登录。", "/wxl/uc/?login=1");
            } else {
                return ModelAndViewUtil.error("注册失败，请稍后再试，或者与我们联系！");
            }
        }
        //2.如果没有openid，进行注册
        else {
            user = new User();
            user.setUserName("");
            user.setNickName(nickName);
            user.setRealName("");
            user.setWechatNickname("");
            user.setOpenId("");
            user.setSex((byte)0);
            user.setCreatedTime(timestamp);
            user.setCreatedUserId(0);
            user.setRoleId(1);
            user.setPhone(phone);
            user.setUserPassword(userPassword);
            user.setUserStatus((byte)1);
            int addUserResult = userMapper.addUser(user);
            System.out.println("none openid new user addUserResult = " + addUserResult);
            if(addUserResult > 0) {
                return ModelAndViewUtil.success("注册成功了，请登录。", "/wxl/uc/?login=1");
            } else {
                return ModelAndViewUtil.error("注册失败，请稍后再试，或者与我们联系！");
            }
        }
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseVO login(@RequestParam Map<String, Object> params, HttpSession session)
    { //@RequestParam("phone") String phone, HttpServletRequest request,
        System.out.println("====执行登录操作======");
        /*System.out.println(request.getParameter("phone"));
        System.out.println(request.getParameter("userPassword"));
        System.out.println(request);*/

        System.out.println(params);
        String phone = params.get("phone").toString();
        String userPassword = (String) params.get("userPassword");
        String openid = params.get("openid").toString();


        //判断是否是手机号码
        if(!ValidateUtil.isPhoneNum(phone)) {
            return ModelAndViewUtil.error("手机号码填写错误，请检查！");
        }

        //通过手机号和密码登陆
        String sql = "SELECT * FROM user WHERE phone = ? LIMIT 1";
        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[]{phone}, new BeanPropertyRowMapper<>(User.class));
            System.out.println("db user = "+user);

            userPassword = Md5Util.Md5Str(userPassword, phone.substring(7));

            if(userPassword.equals(user.getUserPassword())) {
                SessionUtil.regUserSession(user, session);
                return ModelAndViewUtil.success(null, "/wxl/uc/?login=1");
            } else {
                return ModelAndViewUtil.error("用户密码错误！");
            }
        } catch (EmptyResultDataAccessException e) {
            //e.printStackTrace();
            logger.error("EmptyResultDataAccessException = " + e.getMessage());
            return ModelAndViewUtil.error("您的手机号还没有注册，请先进行注册！");
        }
    }

    @GetMapping("/login")
    public ModelAndView login(Model model, HttpServletRequest request, HttpSession session)
    {
        System.out.println("====进入用户登录页面======");
        WeChatUser weChatUser = null;
        if(session.getAttribute("tmp_wechatuser") == null) {
            System.out.println("====session_wechatuser empty===");
            weChatUser = new WeChatUser();
        } else {
            weChatUser = (WeChatUser)session.getAttribute("tmp_wechatuser");
        }

        model.addAttribute("session_wechatuser", weChatUser);

        System.out.println(session.getAttribute("tmp_wechatuser"));
        return ModelAndViewUtil.view("user/login");
    }

    @GetMapping("/reg")
    public ModelAndView reg(Model model, HttpServletRequest request, HttpSession session)
    {
        System.out.println("====进入用户注册页面======");
        WeChatUser weChatUser = null;
        if(session.getAttribute("tmp_wechatuser") == null) {
            System.out.println("====session_wechatuser empty===");
            weChatUser = new WeChatUser();
        } else {
            weChatUser = (WeChatUser)session.getAttribute("tmp_wechatuser");
        }
        System.out.println("===openid:=="+weChatUser.getOpenid());

        model.addAttribute("session_wechatuser",  weChatUser);
        System.out.println("==wechatuser="+weChatUser);
        System.out.println("===session.getAttribute(\"tmp_wechatuser\")=="+session.getAttribute("tmp_wechatuser"));
        return ModelAndViewUtil.view("user/reg");
    }

}