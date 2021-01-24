package com.fundzforus.server.service;

import com.fundzforus.server.dao.mybatis.UserMessageMapper;
import com.fundzforus.server.domain.Message;
import com.fundzforus.server.exception.MessageAlreadyExistException;
import com.fundzforus.server.exception.MessageNotFoundException;
import com.fundzforus.server.exception.MissingMandatoryFieldsException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserMessageServiceImpl implements IUserMessageService {

    @Autowired
    private UserMessageMapper userMessageMapper;

    @Override
    public List<Message> findAllMessagesByTenantId(int tenantId) {
        Map parameterMap = new HashMap();
        parameterMap.put("tenantId", tenantId);
        return userMessageMapper.findAllMessagesByTenantId(parameterMap);
    }

    @Override
    public List<Message> findMessagesByTenantIdAndCategory(int tenantId, String category) {
        Map parameterMap = new HashMap();
        parameterMap.put("tenantId", tenantId);
        parameterMap.put("messageCategory", category);
        return userMessageMapper.findMessagesByCategoryAndTenantId(parameterMap);
    }

    @Override
    public Message getMessageById(int id) {
        Map parameterMap = new HashMap();
        parameterMap.put("id", id);
        return userMessageMapper.getMessageById(parameterMap);
    }

    @Override
    public int createMessage(Message message) {
        if (StringUtils.isBlank(message.getMessageTitle()) || message.getMessageCategory() == null ||
                StringUtils.isBlank(message.getMessageDescription()) || StringUtils.isBlank(message.getTenantId())) {
            throw new MissingMandatoryFieldsException("Message Title, Category, Description and UserId can not be Empty");
        } else {
            Map parameterMap = new HashMap();
            parameterMap.put("messageTitle", message.getMessageTitle());
            parameterMap.put("tenantId", message.getTenantId());
            Message dbMessage = userMessageMapper.findMessageByTitleAndTenantId(parameterMap);
            if (dbMessage != null) {
                throw new MessageAlreadyExistException("Message With Title ::" + message.getMessageTitle() + " already exist");
            }
            message.setCreatedBy("MOBILE_APP");
            message.setUpdatedBy("MOBILE_APP");
            return userMessageMapper.insertMessage(message);
        }
    }

    @Override
    public int updateMessage(Message message) {
        if (StringUtils.isBlank(message.getMessageTitle()) || message.getMessageCategory() == null ||
                StringUtils.isBlank(message.getMessageDescription()) || StringUtils.isBlank(message.getTenantId())) {
            throw new MissingMandatoryFieldsException("Message Title, Category, Description and UserId can not be Empty");
        } else {
            Map parameterMap = new HashMap();
            parameterMap.put("messageTitle", message.getMessageTitle());
            parameterMap.put("tenantId", message.getTenantId());
            Message dbMessage = userMessageMapper.findMessageByTitleAndTenantId(parameterMap);
            if (dbMessage == null) {
                throw new MessageNotFoundException("Message With Title ::" + message.getMessageTitle() + " does not exist");
            }
            message.setId(dbMessage.getId());
            message.setUpdatedBy("MOBILE_APP");
            return userMessageMapper.updateMessage(message);
        }
    }

    @Override
    public int deleteMessage(int messageId) {
        if (messageId == 0) {
            throw new MissingMandatoryFieldsException("Program Id can not be Empty or Zero");
        }

        Map parameterMap = new HashMap();
        parameterMap.put("id", messageId);
        Message dbMessage = userMessageMapper.getMessageById(parameterMap);
        if (dbMessage != null) {
            parameterMap.put("id", dbMessage.getId());
            return userMessageMapper.deleteMessageById(parameterMap);
        } else {
            throw new MessageNotFoundException("Message with Id ::" + messageId + " does not exist");
        }
    }
}
