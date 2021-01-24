package com.fundzforus.server.service;

import com.fundzforus.server.domain.Message;

import java.util.List;

public interface IUserMessageService {
    List<Message> findAllMessagesByTenantId(int tenantId);

    List<Message> findMessagesByTenantIdAndCategory(int tenantId, String category);

    Message getMessageById(int id);

    int createMessage(Message message);

    int updateMessage(Message message);

    int deleteMessage(int messageId);
}