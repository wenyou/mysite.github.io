package cn.stkit.wxl.persistence.mapper;

import cn.stkit.wxl.model.WeChatUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file WeChatUserMpper
 * @desc 微信用户mapper接口
 * @website http://www.stkit.cn/
 * @date 2020/6/20 2:16 下午
 */
@Mapper
public interface WeChatUserMpper
{
    WeChatUser findWeChatUserByOpenid(String openid);

    int addWebChatUser(WeChatUser weChatUser);

    int updateWebChatUser(WeChatUser weChatUser);

}