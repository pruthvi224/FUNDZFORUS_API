package com.fundzforus.server.controller.web;

import com.fundzforus.server.form.LoginForm;
import com.fundzforus.server.form.UserForm;
import com.fundzforus.server.service.IUserVideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@Slf4j
@SessionAttributes("user")
public class NewsFeedController {

    @Autowired
    private IUserVideoService userVideoServiceImpl;

//    @GetMapping("/newsfeed")
//    public String messages(Model model, @ModelAttribute("user") UserForm userForm) {
//        if (userForm == null || !userForm.isLoggedIn()) {
//            model.addAttribute("loginForm", new LoginForm());
//            return "login";
//        } else {
//            model.addAttribute("userForm", userForm);
//            return "news-feed";
//        }
//    }


    @ModelAttribute("user")
    public UserForm user() {
        return new UserForm();
    }
}
