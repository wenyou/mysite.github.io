package cn.stkit.wxl.blog.service;

import cn.stkit.wxl.blog.entity.Message;
import cn.stkit.wxl.blogadmin.entity.Pages;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file MessageService
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/6/30 11:35 上午
 */

public interface MessageService
{
    void saveMessage(Message message);

    int countByRead(Byte read);

    List<Message> findAllByRead(Byte read);

    int count();

    Page<Message> findAllMessageBySearch(Pages pages, String name, String email);

    Message findMessageByMessageId(Integer messageId);

    void updateMessageRead(Integer messageId);

    void deleteMessage(Integer messageId);

    String saveMessageReply(Integer messageId, String replyContent, Integer loginUserId);
}