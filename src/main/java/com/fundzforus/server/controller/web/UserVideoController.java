package com.fundzforus.server.controller.web;

import com.fundzforus.server.domain.UserVideo;
import com.fundzforus.server.form.LoginForm;
import com.fundzforus.server.form.UserForm;
import com.fundzforus.server.service.IUserVideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@SessionAttributes("user")
public class UserVideoController {

    @Autowired
    private IUserVideoService userVideoServiceImpl;

    @GetMapping("/videos")
    public String videos(Model model, @ModelAttribute("user") UserForm userForm) {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else {
            List<UserVideo> userVideos = userVideoServiceImpl.findAllUserVideosByTenantId(Integer.parseInt(userForm.getUserTenantId()));
            userForm.setUserVideos(userVideos);
            model.addAttribute("userForm", userForm);
            return "video-details";
        }
    }

    @GetMapping("/newVideo")
    public String newVideo(Model model, @ModelAttribute("user") UserForm userForm) {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else {
            userForm.setNewVideo(new UserVideo());
            model.addAttribute("userForm", userForm);
            return "add-video";
        }
    }

    @PostMapping("/newVideo")
    public String newVideoSubmit(Model model, @ModelAttribute("user") UserForm userForm) {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else {
            UserVideo userVideo = userForm.getNewVideo();
            userVideo.setTenantId(userForm.getUserTenantId());
            userVideoServiceImpl.createUserVideo(userVideo);
            List<UserVideo> userVideos = userVideoServiceImpl.findAllUserVideosByTenantId(Integer.parseInt(userForm.getUserTenantId()));
            userForm.setUserVideos(userVideos);
            model.addAttribute("userForm", userForm);
            return "video-details";
        }
    }

    @GetMapping("/deleteVideo")
    public String deleteProgram(Model model, @ModelAttribute("user") UserForm userForm, @RequestParam("id") int videoId) {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else {
            userVideoServiceImpl.deleteUserVideo(videoId);
            List<UserVideo> userVideos = userVideoServiceImpl.findAllUserVideosByTenantId(Integer.parseInt(userForm.getUserTenantId()));
            userForm.setUserVideos(userVideos);
            model.addAttribute("userForm", userForm);
            return "video-details";
        }
    }

    @ModelAttribute("user")
    public UserForm user() {
        return new UserForm();
    }
}
