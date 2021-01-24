package com.fundzforus.server.controller.rest;

import com.fundzforus.server.domain.Message;
import com.fundzforus.server.domain.Response;
import com.fundzforus.server.exception.MessageAlreadyExistException;
import com.fundzforus.server.exception.MessageNotFoundException;
import com.fundzforus.server.exception.MissingMandatoryFieldsException;
import com.fundzforus.server.service.IUserMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
public class UserMessagesRestApiController {

    @Autowired
    private IUserMessageService messageServiceImpl;

    @GetMapping(value = "/rest/allUserMessagesByTenantId", produces = "application/json")
    public List<Message> getAllMessages(@RequestHeader String tenantId) {
        return messageServiceImpl.findAllMessagesByTenantId(Integer.parseInt(tenantId));
    }

    @PostMapping(value = "/rest/message", produces = "application/json", consumes = "application/json")
    public Response createMessage(@RequestBody Message message) {
        int rowsInserted = 0;
        try {
            rowsInserted = messageServiceImpl.createMessage(message);
        } catch (MessageAlreadyExistException | MissingMandatoryFieldsException e1) {
            return new Response("failed", LocalDateTime.now(), e1.getMessage());
        }
        if (rowsInserted == 1) {
            return new Response("success", LocalDateTime.now());
        } else {
            return new Response("failed", LocalDateTime.now());
        }
    }

    @PutMapping(value = "/rest/message", produces = "application/json")
    public Response updateMessage(@RequestBody Message message) {
        int rowsUpdated = 0;
        try {
            rowsUpdated = messageServiceImpl.updateMessage(message);
        } catch (MessageNotFoundException | MissingMandatoryFieldsException e1) {
            return new Response("failed", LocalDateTime.now(), e1.getMessage());
        }
        if (rowsUpdated == 1) {
            return new Response("success", LocalDateTime.now());
        } else {
            return new Response("failed", LocalDateTime.now());
        }
    }

    @GetMapping(value = "/rest/message", produces = "application/json")
    public Message getMessage(@RequestHeader String messageId) {
        Message response = messageServiceImpl.getMessageById(Integer.parseInt(messageId));
        return response;
    }


    @GetMapping(value = "/rest/allUserMessagesByByTenantIdAndCategory", produces = "application/json")
    public List<Message> getMessagesByUserIdAndCategory(@RequestHeader String tenantId, @RequestHeader String category) {
        List<Message> response = messageServiceImpl.findMessagesByTenantIdAndCategory(Integer.parseInt(tenantId), category);
        return response;
    }

    @DeleteMapping(value = "/rest/message", produces = "application/json")
    public Response deleteMessage(@RequestHeader String messageId) {
        int rowsDeleted = 0;
        try {
            rowsDeleted = messageServiceImpl.deleteMessage(Integer.parseInt(messageId));
        } catch (MessageNotFoundException | MissingMandatoryFieldsException e1) {
            return new Response("failed", LocalDateTime.now(), e1.getMessage());
        }
        if (rowsDeleted == 1) {
            return new Response("success", LocalDateTime.now());
        } else {
            return new Response("failed", LocalDateTime.now());
        }
    }
}
