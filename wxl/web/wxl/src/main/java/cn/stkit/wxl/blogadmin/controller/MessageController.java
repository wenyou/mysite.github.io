package cn.stkit.wxl.blogadmin.controller;

import cn.stkit.wxl.blog.entity.Message;
import cn.stkit.wxl.blog.service.MessageService;
import cn.stkit.wxl.blogadmin.constants.Constants;
import cn.stkit.wxl.blogadmin.entity.Pages;
import cn.stkit.wxl.object.ResponseVO;
import cn.stkit.wxl.utils.ModelAndViewUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file MessageController
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/7/20 10:58 上午
 */
@Controller
@RequestMapping("/blogAdmin/message")
public class MessageController
{
    @Autowired
    private MessageService messageService;

    @GetMapping("/list")
    public String list(Integer pageNumber, String name, String email, Model model)
    {
        Page<Message> messagePage = messageService.findAllMessageBySearch(Pages.defaultPages(pageNumber), name, email);

        model.addAttribute("messageList", messagePage.getContent());
        model.addAttribute("totalCount", messagePage.getTotalElements());
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("name", name);
        model.addAttribute("email", email);
        model.addAttribute("menuFlag", Constants.MESSAGE_MENU_FLAG);

        return "blog/admin/message";
    }

    @GetMapping("/messageInfo")
    public String messageInfo(Integer messageId, Model model)
    {
        Message message = messageService.findMessageByMessageId(messageId);
        if(message.getRead() == 0) {
            messageService.updateMessageRead(messageId);
        }
        model.addAttribute("message", message);
        model.addAttribute("menuFlag", Constants.MESSAGE_MENU_FLAG);
        return "blog/admin/messageEdit";
    }

    @PostMapping("/saveOrUpdateReply")
    @ResponseBody
    public ResponseVO saveOrUpdateReply(@RequestBody Message message)
    {
        String result = messageService.saveMessageReply(message.getMessageId(), message.getReplyContent(), message.getLoginUserId());
        if("success".equals(result)) {
            return ModelAndViewUtil.success("回复用户留言成功！", "/blogAdmin/message/list");
        } else {
            return ModelAndViewUtil.error(result);
        }
    }

    @PostMapping("/delete")
    @ResponseBody
    public void delete(Integer messageId)
    {
        messageService.deleteMessage(messageId);
    }
}