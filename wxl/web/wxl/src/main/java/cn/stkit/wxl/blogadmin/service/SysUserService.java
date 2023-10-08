package cn.stkit.wxl.blogadmin.service;

import cn.stkit.wxl.blogadmin.entity.Pages;
import cn.stkit.wxl.blogadmin.entity.SysUser;
import org.springframework.data.domain.Page;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file SysUserService
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/7/2 6:15 下午
 */

public interface SysUserService
{
    Page<SysUser> findAllBySearch(Pages pages, Integer userId, String userName);

    Page<SysUser> findAllBySearch(Pages pages, Integer userId, String userName, String phone, String wechatNickname, String openId,String realName,Byte userStatus);

    SysUser findSysUserByUserId(Integer userId);

    String saveOrUpdateUser(SysUser sysUser);

    int updateUserUserStatus(Integer userId, Byte userStatus);

    int deleteUser(Integer userId);

    SysUser findByUserName(String userName);

    //后台管理员添加或修改用户信息
    String adminSaveOrUpdateUser(SysUser sysUser);
}