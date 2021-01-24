package com.fundzforus.server.controller.web;

import com.fundzforus.server.domain.Tenant;
import com.fundzforus.server.domain.User;
import com.fundzforus.server.domain.UserVideo;
import com.fundzforus.server.form.LoginForm;
import com.fundzforus.server.form.UserForm;
import com.fundzforus.server.service.ITenantService;
import com.fundzforus.server.service.IUserService;
import com.fundzforus.server.service.IUserVideoService;
import com.fundzforus.server.util.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@SessionAttributes("user")
public class LoginController {

    @Value("${local.images.location}")
    String localImagesLocation;

    @Autowired
    private IUserService userServiceImpl;

    @Autowired
    private ITenantService tenantServiceImpl;

    @Autowired
    private IUserVideoService userVideoServiceImpl;

    @Autowired
    FileUploadUtil fileUploadUtil;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @GetMapping("/logout")
    public String logout(Model model, @ModelAttribute("user") UserForm userForm) {
        userForm.setLoggedIn(false);
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @GetMapping("/home")
    public String homepage(Model model, @ModelAttribute("user") UserForm userForm) {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else {
            List<UserVideo> userVideos = userVideoServiceImpl.findAllUserVideosByTenantId(Integer.parseInt(userForm.getUserTenantId()));
            userForm.setUserVideos(userVideos);
            model.addAttribute("userForm", userForm);
            return "user-home";
        }
    }

    @GetMapping("/editprofile")
    public String editProfile(@ModelAttribute("user") UserForm userForm, Model model) {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else {
            List<Tenant> allTenants = tenantServiceImpl.findAllTenants();
            userForm.setTenants(allTenants);
            model.addAttribute("userForm", userForm);
            return "edit-profile";
        }
    }

    @PostMapping("/editprofile")
    public String updateProfile(@ModelAttribute("user") UserForm userForm, Model model,
                                @RequestParam("multiPartImage") MultipartFile multipartFile) throws IOException {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else {

            String fileName = org.springframework.util.StringUtils.cleanPath(multipartFile.getOriginalFilename());

            if (StringUtils.isBlank(userForm.getFirstName()) || StringUtils.isBlank(userForm.getLastName()) ||
                    StringUtils.isBlank(userForm.getPhone()) || StringUtils.isBlank(userForm.getEmail()) ||
                    StringUtils.isBlank(userForm.getLocation()) || StringUtils.isBlank(userForm.getUserTenantId())) {
                userForm.setMessage("Mandatory Fields are missing");
                List<Tenant> allTenants = tenantServiceImpl.findAllTenants();
                log.info("Finding all tenants is completed");
                userForm.setTenants(allTenants);
                model.addAttribute("userForm", userForm);
                return "edit-profile";
            }

            Tenant dbTenant = tenantServiceImpl.getById(Integer.parseInt(userForm.getUserTenantId()));
            if (dbTenant == null) {
                userForm.setMessage("Tenant is not selected missing");
                List<Tenant> allTenants = tenantServiceImpl.findAllTenants();
                userForm.setTenants(allTenants);
                model.addAttribute("userForm", userForm);
                return "edit-profile";
            }

            User user = new User();
            user.setFirstName(userForm.getFirstName());
            user.setLastName(userForm.getLastName());
            user.setPhone(userForm.getPhone());
            user.setEmail(userForm.getEmail());
            user.setLocation(userForm.getLocation());
            user.setReceiveNotification(userForm.isReceiveNotification());
            user.setImageUrl(fileName);
            user.setTenantId(Integer.parseInt(userForm.getUserTenantId()));

            Map parameterMap = new HashMap();
            parameterMap.put("email", user.getEmail());
            User dbUser = userServiceImpl.findUserWithEmail(user.getEmail());
            model.addAttribute("userForm", userForm);
            if (dbUser != null) {
                user.setId(dbUser.getId());

                if (StringUtils.isNotBlank(userForm.getPassword())) {
                    user.setPassword(userForm.getPassword());
                } else {
                    user.setPassword(dbUser.getPassword());
                }

                int rowsInserted = userServiceImpl.updateUser(user);
                if (rowsInserted > 0) {
                    String fileUrl = "";
                    if (StringUtils.isNotBlank(fileName)) {
                        fileUrl = fileUploadUtil.saveImages(dbUser.getId(), fileName, multipartFile, "user");
                        user.setId(dbUser.getId());
                        user.setImageUrl(fileUrl);
                        userServiceImpl.updateUser(user);
                    }

                    userForm.setImageUrl(fileUrl);
                    userForm.setMessage("User Profile Update Successfully");
                    List<UserVideo> userVideos = userVideoServiceImpl.findAllUserVideosByTenantId(Integer.parseInt(userForm.getUserTenantId()));
                    if (userVideos != null && userVideos.size() > 0) {
                        if (userVideos.size() == 3) {
                            userForm.setFirstVideoExist(true);
                            userForm.setSecondVideoExist(true);
                            userForm.setThirdVideoExist(true);
                        } else if (userVideos.size() == 2) {
                            userForm.setFirstVideoExist(true);
                            userForm.setSecondVideoExist(true);
                        } else if (userVideos.size() == 1) {
                            userForm.setFirstVideoExist(true);
                        }
                    }
                    userForm.setUserVideos(userVideos);
                    return "user-home";
                } else {
                    userForm.setMessage("User does not exist");
                    model.addAttribute("loginForm", new LoginForm());
                    return "login";
                }
            } else {
                userForm.setMessage("User does not exist");
                model.addAttribute("loginForm", new LoginForm());
                return "login";
            }
        }
    }

    @PostMapping("/login")
    public String loginSubmit(@ModelAttribute LoginForm loginForm, Model model, @ModelAttribute("user") UserForm userForm) {
        System.out.println(loginForm.getEmail());
        System.out.println(loginForm.getPassword());
        User dbUser = userServiceImpl.findUserWithEmailAndPassword(loginForm.getEmail(),
                loginForm.getPassword());
        if (dbUser != null) {
            userForm.setUserId(dbUser.getId());
            userForm.setLoggedIn(true);
            userForm.setPassword(dbUser.getPassword());
            userForm.setFirstName(dbUser.getFirstName());
            userForm.setLastName(dbUser.getLastName());
            userForm.setPhone(dbUser.getPhone());
            userForm.setEmail(dbUser.getEmail());
            userForm.setLocation(dbUser.getLocation());
            userForm.setReceiveNotification(dbUser.getReceiveNotification());
            userForm.setImageUrl(dbUser.getImageUrl());
            if (dbUser.getTenantId() != null) {
                userForm.setUserTenantId(dbUser.getTenantId().toString());
            }
            List<UserVideo> userVideos = userVideoServiceImpl.findAllUserVideosByTenantId(Integer.parseInt(userForm.getUserTenantId()));
            userForm.setUserVideos(userVideos);
            if (userVideos != null && userVideos.size() > 0) {
                if (userVideos.size() == 3) {
                    userForm.setFirstVideoExist(true);
                    userForm.setSecondVideoExist(true);
                    userForm.setThirdVideoExist(true);
                } else if (userVideos.size() == 2) {
                    userForm.setFirstVideoExist(true);
                    userForm.setSecondVideoExist(true);
                } else if (userVideos.size() == 1) {
                    userForm.setFirstVideoExist(true);
                }
            }
            model.addAttribute("userForm", userForm);
            return "user-home";
        } else {
            loginForm.setMessage("Invalid Email Address and Password");
            model.addAttribute("loginForm", loginForm);
            return "login";
        }
    }

    @ModelAttribute("user")
    public UserForm user() {
        return new UserForm();
    }
}