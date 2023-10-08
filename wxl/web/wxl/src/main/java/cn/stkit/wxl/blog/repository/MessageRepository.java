package cn.stkit.wxl.blog.repository;

import cn.stkit.wxl.blog.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file MessageRepository
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/6/29 5:56 下午
 */

public interface MessageRepository extends JpaRepository<Message, Integer>, JpaSpecificationExecutor<Message>
{
    int countByRead(Byte read);

    List<Message> findAllByRead(Byte read);
}