package com.fundzforus.server.dao.mybatis;

import com.fundzforus.server.domain.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMessageMapper {
    List<Message> findMessagesByCategoryAndTenantId(Map<String, String> map);

    List<Message> findAllMessagesByTenantId(Map<String, String> map);

    Message findMessageByTitleAndTenantId(Map<String, String> map);

    Message getMessageById(Map<String, String> map);

    int insertMessage(Message message);

    int updateMessage(Message message);

    int deleteMessageById(Map<String, String> map);
}