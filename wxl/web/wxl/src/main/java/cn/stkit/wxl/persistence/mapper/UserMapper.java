package cn.stkit.wxl.persistence.mapper;

import cn.stkit.wxl.persistence.beans.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file UserMapper
 * @desc 用户mapper接口
 * @website http://www.stkit.cn/
 * @date 2020/6/19 6:14 下午
 */
@Mapper
public interface UserMapper
{
    //通过userId查找用户
    User findUserByUserId(int userId);

    //通过手机号查找用户
    User findUserByPhone(String phone);

    //通过openid查找用户
    User findUserByOpenId(String openid);

    //添加用户
    Integer addUser(User user);

    //更新用户手机号
    int updateUserPhoneByUserId(User user);

    //更新用户的openid,更新用户微信信息
    Integer updateUserOpenIDByUserId(User user);

    List<User> getUserList();

    Integer updateUserPasswordByUserId(User user);

    Integer deleteUserByUserId(Integer userId);
}