package com.fundzforus.server.controller.web;

import com.fundzforus.server.domain.Tenant;
import com.fundzforus.server.domain.User;
import com.fundzforus.server.form.LoginForm;
import com.fundzforus.server.form.UserForm;
import com.fundzforus.server.service.ITenantService;
import com.fundzforus.server.service.IUserService;
import com.fundzforus.server.util.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class UserRegistrationController {

    @Autowired
    private IUserService userServiceImpl;

    @Autowired
    private ITenantService tenantServiceImpl;

    @Autowired
    FileUploadUtil fileUploadUtil;

    @GetMapping("/registration")
    public String registration(Model model) {

        UserForm userForm = new UserForm();

        List<Tenant> allTenants = tenantServiceImpl.findAllTenants();
        userForm.setTenants(allTenants);
        model.addAttribute("userForm", userForm);

        return "user-registration";
    }

    @PostMapping("/registration")
    public String registrationSubmit(@ModelAttribute UserForm userForm, Model model,
                                     @RequestParam("multiPartImage") MultipartFile multipartFile) throws IOException {

        String fileName = org.springframework.util.StringUtils.cleanPath(multipartFile.getOriginalFilename());

        if (StringUtils.isBlank(userForm.getPassword()) || StringUtils.isBlank(userForm.getFirstName()) ||
                StringUtils.isBlank(userForm.getLastName()) || StringUtils.isBlank(userForm.getPhone()) ||
                StringUtils.isBlank(userForm.getEmail()) || StringUtils.isBlank(userForm.getLocation()) ||
                StringUtils.isBlank(userForm.getUserTenantId())) {
            userForm.setMessage("User Email, Password, FirstName, LastName, Phone , Location and Tenants are missing");
            List<Tenant> allTenants = tenantServiceImpl.findAllTenants();
            userForm.setTenants(allTenants);
            model.addAttribute("userForm", userForm);
            return "user-registration";
        }

        Tenant dbTenant = tenantServiceImpl.getById(Integer.parseInt(userForm.getUserTenantId()));
        if (dbTenant == null) {
            userForm.setMessage("Tenant is not selected");
            List<Tenant> allTenants = tenantServiceImpl.findAllTenants();
            userForm.setTenants(allTenants);
            model.addAttribute("userForm", userForm);
            return "user-registration";
        }

        User user = new User();
        user.setPassword(userForm.getPassword());
        user.setFirstName(userForm.getFirstName());
        user.setLastName(userForm.getLastName());
        user.setPhone(userForm.getPhone());
        user.setEmail(userForm.getEmail());
        user.setLocation(userForm.getLocation());
        user.setReceiveNotification(userForm.isReceiveNotification());
        user.setTenantId(Integer.parseInt(userForm.getUserTenantId()));
        user.setImageUrl(fileName);

        Map parameterMap = new HashMap();
        parameterMap.put("email", user.getEmail());
        User dbUser = userServiceImpl.findUserWithEmail(user.getEmail());
        model.addAttribute("userForm", userForm);
        if (dbUser == null) {
            int rowsInserted = userServiceImpl.createUser(user);
            if (rowsInserted > 0) {
                dbUser = userServiceImpl.findUserWithEmail(user.getEmail());

                String fileUrl = "";
                if (StringUtils.isNotBlank(fileName)) {
                    fileUrl = fileUploadUtil.saveImages(dbUser.getId(), fileName, multipartFile, "user");
                    user.setId(dbUser.getId());
                    user.setImageUrl(fileUrl);
                    userServiceImpl.updateUser(user);
                }

                LoginForm loginForm = new LoginForm();
                loginForm.setMessage("User Registered Successfully");
                model.addAttribute("loginForm", loginForm);
                return "login";
            } else {
                userForm.setMessage("There is some other problem");
                return "user-registration";
            }
        } else {
            List<Tenant> allTenants = tenantServiceImpl.findAllTenants();
            userForm.setTenants(allTenants);
            userForm.setMessage("User is already exist. Please enter different User name.");
            return "user-registration";
        }
    }
}
