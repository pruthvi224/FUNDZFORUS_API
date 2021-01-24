package com.fundzforus.server.controller.web;

import com.fundzforus.server.domain.Message;
import com.fundzforus.server.domain.MessageCategory;
import com.fundzforus.server.form.LoginForm;
import com.fundzforus.server.form.UserForm;
import com.fundzforus.server.service.IUserMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Controller
@Slf4j
@SessionAttributes("user")
public class UserMessagesController {
    @Autowired
    private IUserMessageService messageServiceImpl;

    @GetMapping("/messages")
    public String messages(Model model, @ModelAttribute("user") UserForm userForm, @RequestParam("category") String category) {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else if (StringUtils.equalsIgnoreCase(category, "all")) {
            List<Message> messages = messageServiceImpl.findAllMessagesByTenantId(Integer.parseInt(userForm.getUserTenantId()));
            userForm.setMessages(messages);
            model.addAttribute("userForm", userForm);
            return "message-board";
        } else if (StringUtils.equalsIgnoreCase(category, "one")) {
            List<Message> messages = messageServiceImpl.findMessagesByTenantIdAndCategory(Integer.parseInt(userForm.getUserTenantId()),
                    MessageCategory.CATEGORY_1.name());
            userForm.setMessages(messages);
            model.addAttribute("userForm", userForm);
            return "message-board-category1";
        } else if (StringUtils.equalsIgnoreCase(category, "two")) {
            List<Message> messages = messageServiceImpl.findMessagesByTenantIdAndCategory(Integer.parseInt(userForm.getUserTenantId()),
                    MessageCategory.CATEGORY_2.name());
            userForm.setMessages(messages);
            model.addAttribute("userForm", userForm);
            return "message-board-category2";
        } else if (StringUtils.equalsIgnoreCase(category, "three")) {
            List<Message> messages = messageServiceImpl.findMessagesByTenantIdAndCategory(Integer.parseInt(userForm.getUserTenantId()),
                    MessageCategory.CATEGORY_3.name());
            userForm.setMessages(messages);
            model.addAttribute("userForm", userForm);
            return "message-board-category3";
        } else {
            List<Message> messages = messageServiceImpl.findAllMessagesByTenantId(Integer.parseInt(userForm.getUserTenantId()));
            userForm.setMessages(messages);
            model.addAttribute("userForm", userForm);
            return "message-board";
        }
    }

    @GetMapping("/newMessage")
    public String newMessage(Model model, @ModelAttribute("user") UserForm userForm) {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else {
            userForm.setNewMessage(new Message());
            model.addAttribute("userForm", userForm);
            return "add-message";
        }
    }

    @PostMapping("/newMessage")
    public String addMessage(Model model, @ModelAttribute("user") UserForm userForm) {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else {
            Message newMessage = userForm.getNewMessage();
            newMessage.setTenantId(userForm.getUserTenantId());
            messageServiceImpl.createMessage(newMessage);
            List<Message> messages = messageServiceImpl.findAllMessagesByTenantId(Integer.parseInt(userForm.getUserTenantId()));
            userForm.setMessages(messages);
            model.addAttribute("userForm", userForm);
            return "message-board";
        }
    }

    @GetMapping("/deleteMessage")
    public String programConfirmation(Model model, @ModelAttribute("user") UserForm userForm, @RequestParam("id") int messageId, @RequestParam("category") String category) {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else {
            messageServiceImpl.deleteMessage(messageId);
            if (StringUtils.equalsIgnoreCase(category, "all")) {
                List<Message> messages = messageServiceImpl.findAllMessagesByTenantId(Integer.parseInt(userForm.getUserTenantId()));
                userForm.setMessages(messages);
                model.addAttribute("userForm", userForm);
                return "message-board";
            } else if (StringUtils.equalsIgnoreCase(category, "one")) {
                List<Message> messages = messageServiceImpl.findMessagesByTenantIdAndCategory(Integer.parseInt(userForm.getUserTenantId()),
                        MessageCategory.CATEGORY_1.name());
                userForm.setMessages(messages);
                model.addAttribute("userForm", userForm);
                return "message-board-category1";
            } else if (StringUtils.equalsIgnoreCase(category, "two")) {
                List<Message> messages = messageServiceImpl.findMessagesByTenantIdAndCategory(Integer.parseInt(userForm.getUserTenantId()),
                        MessageCategory.CATEGORY_2.name());
                userForm.setMessages(messages);
                model.addAttribute("userForm", userForm);
                return "message-board-category2";
            } else if (StringUtils.equalsIgnoreCase(category, "three")) {
                List<Message> messages = messageServiceImpl.findMessagesByTenantIdAndCategory(Integer.parseInt(userForm.getUserTenantId()),
                        MessageCategory.CATEGORY_3.name());
                userForm.setMessages(messages);
                model.addAttribute("userForm", userForm);
                return "message-board-category3";
            } else {
                List<Message> messages = messageServiceImpl.findAllMessagesByTenantId(Integer.parseInt(userForm.getUserTenantId()));
                userForm.setMessages(messages);
                model.addAttribute("userForm", userForm);
                return "message-board";
            }
        }
    }

    @ModelAttribute("user")
    public UserForm user() {
        return new UserForm();
    }
}
