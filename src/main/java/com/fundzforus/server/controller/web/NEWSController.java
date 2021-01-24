package com.fundzforus.server.controller.web;

import com.fundzforus.server.domain.NewsReader;
import com.fundzforus.server.domain.UserVideo;
import com.fundzforus.server.form.LoginForm;
import com.fundzforus.server.form.UserForm;
import com.fundzforus.server.service.INEWSReaderService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@SessionAttributes("user")
public class NEWSController {

    @Autowired
    private INEWSReaderService newsReaderServiceImpl;

    @GetMapping("/newsfeed")
    public String getNEWS(Model model, @ModelAttribute("user") UserForm userForm) {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else {
            List<NewsReader> newsList = newsReaderServiceImpl.findAllNEWS();
            List<NewsReader> tempNewsList = new ArrayList<>(newsList);
            List<List<NewsReader>> newsSubList = Lists.partition(tempNewsList, 2);
            for (List<NewsReader> subList : newsSubList) {
                if (subList.size() == 1) {
                    subList.add(new NewsReader());
                }
            }
            userForm.setNewsReaderList(newsList);
            userForm.setNewsReaderSubList(newsSubList);
            model.addAttribute("userForm", userForm);
            return "news-feed";
        }
    }

    @GetMapping("/newsFeedDetails")
    public String getNEWSFeedDetails(Model model, @ModelAttribute("user") UserForm userForm, @RequestParam("id") int id) {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else {
            NewsReader newsReader = newsReaderServiceImpl.getNEWSById(id);
            if(newsReader == null) {
                return "news-feed";
            }
            userForm.setNewsReader(newsReader);
            model.addAttribute("userForm", userForm);
            return "news-feed-details";
        }
    }

    @GetMapping("/addNews")
    public String insertNEWS(Model model, @ModelAttribute("user") UserForm userForm) {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else {
            userForm.setNewsReaderNew(new NewsReader());
            model.addAttribute("userForm", userForm);
            return "add-news";
        }
    }

    @PostMapping("/addNews")
    public String submitNEWS(Model model, @ModelAttribute("user") UserForm userForm) {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else {
            NewsReader newsReader = userForm.getNewsReaderNew();
            newsReaderServiceImpl.createNews(newsReader);
            List<NewsReader> newsList = newsReaderServiceImpl.findAllNEWS();
            List<NewsReader> tempNewsList = new ArrayList<>(newsList);
            List<List<NewsReader>> newsSubList = Lists.partition(tempNewsList, 2);
            for (List<NewsReader> subList : newsSubList) {
                if (subList.size() == 1) {
                    subList.add(new NewsReader());
                }
            }
            userForm.setNewsReaderList(newsList);
            userForm.setNewsReaderSubList(newsSubList);
            model.addAttribute("userForm", userForm);
            return "news-feed";
        }
    }
//
//    @GetMapping("/deleteNEWS")
//    public String deleteNEWS(Model model, @ModelAttribute("user") UserForm userForm, @RequestParam("id") int videoId) {
//        if (userForm == null || !userForm.isLoggedIn()) {
//            model.addAttribute("loginForm", new LoginForm());
//            return "login";
//        } else {
//            newsReaderServiceImpl.deleteUserVideo(videoId);
//            List<UserVideo> userVideos = newsReaderServiceImpl.findAllUserVideosByTenantId(Integer.parseInt(userForm.getUserTenantId()));
//            userForm.setUserVideos(userVideos);
//            model.addAttribute("userForm", userForm);
//            return "video-details";
//        }
//    }

    @ModelAttribute("user")
    public UserForm user() {
        return new UserForm();
    }
}
