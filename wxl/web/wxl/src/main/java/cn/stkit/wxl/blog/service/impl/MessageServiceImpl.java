package cn.stkit.wxl.blog.service.impl;

import cn.stkit.wxl.blog.entity.Message;
import cn.stkit.wxl.blog.repository.MessageRepository;
import cn.stkit.wxl.blog.service.MessageService;
import cn.stkit.wxl.blogadmin.entity.Pages;
import cn.stkit.wxl.utils.TimeUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file MessageServiceImpl
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/6/30 11:36 上午
 */
@Service
public class MessageServiceImpl implements MessageService
{
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public void saveMessage(Message message)
    {
        messageRepository.save(message);
    }

    @Override
    public int countByRead(Byte read)
    {
        return messageRepository.countByRead(read);
    }

    @Override
    public List<Message> findAllByRead(Byte read)
    {
        return messageRepository.findAllByRead(read);
    }

    @Override
    public int count()
    {
        return (int) messageRepository.count();
    }

    @Override
    public Page<Message> findAllMessageBySearch(Pages pages, String name, String email)
    {
        Pageable pageable = PageRequest.of(pages.getPage(), pages.getPageSize(), Sort.Direction.DESC, "read", "messageInputDate");

        return messageRepository.findAll(this.getWhereClause(name, email), pageable);
    }

    @Override
    public Message findMessageByMessageId(Integer messageId)
    {
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if (optionalMessage.isPresent()) {
            return optionalMessage.get();
        }
        return null;
        //return messageRepository.findById(messageId).get();
    }

    @Override
    public void updateMessageRead(Integer messageId)
    {
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if(optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            message.setRead((byte)1);
            messageRepository.save(message);
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteMessage(Integer messageId)
    {
        messageRepository.deleteById(messageId);
    }

    /**
     * 保存回复信息
     * @param messageId
     * @param replyContent
     * @param loginUserId
     * @return
     */
    @Override
    public String saveMessageReply(Integer messageId, String replyContent, Integer loginUserId)
    {
        Message message = messageRepository.findById(messageId).get();
        message.setReplyContent(replyContent);
        message.setReplied((byte)1);
        message.setReplyTime(TimeUtil.getNowTimestamp());
        message.setReplyUserId(loginUserId);
        messageRepository.save(message);
        return "success";
    }

    private Specification<Message> getWhereClause(String name, String email)
    {
        return new Specification<Message>() {
            @Override
            public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(StringUtils.isNotBlank(name)) {
                    predicates.add(
                            criteriaBuilder.or(criteriaBuilder.like(root.get("name"), name + "%"))
                    );
                }
                if(StringUtils.isNotBlank(email)) {
                    predicates.add(
                            criteriaBuilder.or(criteriaBuilder.like(root.get("email"), email + "%"))
                    );
                }
                Predicate[] pre = new Predicate[predicates.size()];
                return criteriaQuery.where(predicates.toArray(pre)).getRestriction();
            }
        };
    }
}