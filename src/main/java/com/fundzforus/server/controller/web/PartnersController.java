package com.fundzforus.server.controller.web;

import com.fundzforus.server.domain.Partner;
import com.fundzforus.server.domain.PartnerImage;
import com.fundzforus.server.domain.PartnerVideo;
import com.fundzforus.server.exception.MissingMandatoryFieldsException;
import com.fundzforus.server.exception.PartnerAlreadyExistException;
import com.fundzforus.server.form.LoginForm;
import com.fundzforus.server.form.UserForm;
import com.fundzforus.server.service.IPartnerService;
import com.fundzforus.server.util.FileUploadUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
@SessionAttributes("user")
public class PartnersController {
    @Autowired
    private IPartnerService partnerServiceImpl;

    @Autowired
    FileUploadUtil fileUploadUtil;

    @GetMapping("/newpartner")
    public String newPartner(Model model, @ModelAttribute("user") UserForm userForm) {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else {
            model.addAttribute("userForm", userForm);
            return "become-partner";
        }
    }

    @PostMapping("/newpartner")
    public String becomePartner(Model model, @ModelAttribute("user") UserForm userForm,
                                @RequestParam("multiPartImage") MultipartFile multipartFile) throws Exception {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else {
            String fileName = org.springframework.util.StringUtils.cleanPath(multipartFile.getOriginalFilename());
            Partner newPartner = userForm.getNewPartner();
            newPartner.setLogoURL(fileName);

            try {
                partnerServiceImpl.createPartner(newPartner);
                Partner dbPartner = partnerServiceImpl.findPartnerWithPartnerName(newPartner.getPartnerName());

                String fileUrl = "";
                if (StringUtils.isNotBlank(fileName)) {
                    fileUrl = fileUploadUtil.saveImages(dbPartner.getId(), fileName, multipartFile, "partner");
                    newPartner.setId(dbPartner.getId());
                    newPartner.setLogoURL(fileUrl);
                    partnerServiceImpl.updatePartner(newPartner);
                }

            } catch (PartnerAlreadyExistException e) {
                userForm.setMessage(e.getMessage());
                model.addAttribute("userForm", userForm);
                return "become-partner";
            } catch (MissingMandatoryFieldsException e) {
                userForm.setMessage(e.getMessage());
                model.addAttribute("userForm", userForm);
                return "become-partner";
            }
            List<Partner> partners = partnerServiceImpl.findAllPartners();
            userForm.setPartners(partners);
            model.addAttribute("userForm", userForm);
            return "thankyou-partner";
        }
    }

    @GetMapping("/partners")
    public String partners(Model model, @ModelAttribute("user") UserForm userForm) {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else {
            List<Partner> partners = partnerServiceImpl.findAllPartners();
            List<List<Partner>> partnersSubList = Lists.partition(partners, 2);
            for (List<Partner> subList : partnersSubList) {
                if (subList.size() == 1) {
                    subList.add(new Partner());
                }
            }
            //userForm.setPartners(partners);
            userForm.setPartnersList(partnersSubList);
            model.addAttribute("userForm", userForm);
            return "partners";
        }
    }


    @GetMapping("/partnerDetails")
    public String partnerDetails(Model model, @ModelAttribute("user") UserForm userForm, @RequestParam("id") int partnerId) {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else {
            Partner partner = partnerServiceImpl.getPartnerById(partnerId);
            List<PartnerImage> partnerImages = partnerServiceImpl.findPartnerImagesByPartnerId(partnerId);
            List<List<PartnerImage>> partnerImagesSubList = Lists.partition(partnerImages, 2);
            for (List<PartnerImage> subList : partnerImagesSubList) {
                if (subList.size() == 1) {
                    subList.add(new PartnerImage());
                }
            }
            userForm.setPartnerImages(partnerImages);
            userForm.setPartnerImagesList(partnerImagesSubList);

            List<PartnerVideo> partnerVideos = partnerServiceImpl.findPartnerVideosByPartnerId(partnerId);
            userForm.setPartner(partner);
            userForm.setPartnerVideos(partnerVideos);
            model.addAttribute("userForm", userForm);
            return "partnerDetail";
        }
    }

    @GetMapping("/addPartnerImage")
    public String addPartnerImage(Model model, @ModelAttribute("user") UserForm userForm, @RequestParam("id") int partnerId) {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else {
            Partner partner = partnerServiceImpl.getPartnerById(partnerId);
            userForm.setPartner(partner);
            userForm.setNewPartnerImage(new PartnerImage());
            model.addAttribute("userForm", userForm);
            return "add-partner-Image";
        }
    }

    @PostMapping("/addPartnerImage")
    public String addPartnerImageSubmitted(Model model, @ModelAttribute("user") UserForm userForm, @RequestParam("id") int partnerId,
                                            @RequestParam("multiPartImage") MultipartFile multipartFile) throws IOException {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else {
            String fileName = org.springframework.util.StringUtils.cleanPath(multipartFile.getOriginalFilename());
            if (StringUtils.isBlank(fileName)) {
                userForm.setMessage("Image is not attached");
                model.addAttribute("userForm", userForm);
                return "add-partner-Image";
            }

            Partner partner = partnerServiceImpl.getPartnerById(partnerId);

            String fileUrl = "";
            if (StringUtils.isNotBlank(fileName)) {
                fileUrl = fileUploadUtil.saveImages(partnerId, fileName, multipartFile, "partnerImages");
                PartnerImage partnerImage = userForm.getNewPartnerImage();
                partnerImage.setPartnerId(partnerId + "");
                partnerImage.setImgURL(fileUrl);
                partnerServiceImpl.createPartnerImage(partnerImage);
            }

            List<PartnerImage> partnerImages = partnerServiceImpl.findPartnerImagesByPartnerId(partnerId);
            List<List<PartnerImage>> partnerImagesSubList = Lists.partition(partnerImages, 2);
            for (List<PartnerImage> subList : partnerImagesSubList) {
                if (subList.size() == 1) {
                    subList.add(new PartnerImage());
                }
            }
            userForm.setPartnerImages(partnerImages);
            userForm.setPartnerImagesList(partnerImagesSubList);

            List<PartnerVideo> partnerVideos = partnerServiceImpl.findPartnerVideosByPartnerId(partnerId);
            userForm.setPartner(partner);
            userForm.setPartnerVideos(partnerVideos);
            model.addAttribute("userForm", userForm);
            return "partnerDetail";
        }
    }

    @GetMapping("/addPartnerVideo")
    public String addPartnerVideo(Model model, @ModelAttribute("user") UserForm userForm, @RequestParam("id") int partnerId) {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else {
            Partner partner = partnerServiceImpl.getPartnerById(partnerId);
            userForm.setPartner(partner);
            userForm.setNewPartnerVideo(new PartnerVideo());
            model.addAttribute("userForm", userForm);
            return "add-partner-Video";
        }
    }

    @PostMapping("/addPartnerVideo")
    public String addPartnerVideoSubmitted(Model model, @ModelAttribute("user") UserForm userForm, @RequestParam("id") int partnerId) {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else {

            PartnerVideo partnerVideo = userForm.getNewPartnerVideo();
            partnerVideo.setPartnerId(partnerId + "");
            partnerServiceImpl.createPartnerVideo(partnerVideo);

            Partner partner = partnerServiceImpl.getPartnerById(partnerId);

            List<PartnerImage> partnerImages = partnerServiceImpl.findPartnerImagesByPartnerId(partnerId);
            List<List<PartnerImage>> partnerImagesSubList = Lists.partition(partnerImages, 2);
            for (List<PartnerImage> subList : partnerImagesSubList) {
                if (subList.size() == 1) {
                    subList.add(new PartnerImage());
                }
            }
            userForm.setPartnerImages(partnerImages);
            userForm.setPartnerImagesList(partnerImagesSubList);

            List<PartnerVideo> partnerVideos = partnerServiceImpl.findPartnerVideosByPartnerId(partnerId);
            userForm.setPartner(partner);
            userForm.setPartnerVideos(partnerVideos);
            model.addAttribute("userForm", userForm);
            return "partnerDetail";
        }
    }

    @GetMapping("/deletePartnerImages")
    public String deletePartnerImages(Model model, @ModelAttribute("user") UserForm userForm, @RequestParam("id") int partnerId) {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else {
            partnerServiceImpl.deletePartnerImageByPartnerId(partnerId);
            Partner partner = partnerServiceImpl.getPartnerById(partnerId);

            List<PartnerImage> partnerImages = partnerServiceImpl.findPartnerImagesByPartnerId(partnerId);
            List<List<PartnerImage>> partnerImagesSubList = Lists.partition(partnerImages, 2);
            for (List<PartnerImage> subList : partnerImagesSubList) {
                if (subList.size() == 1) {
                    subList.add(new PartnerImage());
                }
            }
            userForm.setPartnerImages(partnerImages);
            userForm.setPartnerImagesList(partnerImagesSubList);

            List<PartnerVideo> partnerVideos = partnerServiceImpl.findPartnerVideosByPartnerId(partnerId);
            userForm.setPartner(partner);
            userForm.setPartnerVideos(partnerVideos);
            model.addAttribute("userForm", userForm);
            return "partnerDetail";
        }
    }

    @GetMapping("/deletePartnerVideos")
    public String deletePartnerVideos(Model model, @ModelAttribute("user") UserForm userForm, @RequestParam("id") int partnerId) {
        if (userForm == null || !userForm.isLoggedIn()) {
            model.addAttribute("loginForm", new LoginForm());
            return "login";
        } else {
            partnerServiceImpl.deletePartnerVideoByPartnerId(partnerId);
            Partner partner = partnerServiceImpl.getPartnerById(partnerId);

            List<PartnerImage> partnerImages = partnerServiceImpl.findPartnerImagesByPartnerId(partnerId);
            List<List<PartnerImage>> partnerImagesSubList = Lists.partition(partnerImages, 2);
            for (List<PartnerImage> subList : partnerImagesSubList) {
                if (subList.size() == 1) {
                    subList.add(new PartnerImage());
                }
            }
            userForm.setPartnerImages(partnerImages);
            userForm.setPartnerImagesList(partnerImagesSubList);

            List<PartnerVideo> partnerVideos = partnerServiceImpl.findPartnerVideosByPartnerId(partnerId);
            userForm.setPartner(partner);
            userForm.setPartnerVideos(partnerVideos);
            model.addAttribute("userForm", userForm);
            return "partnerDetail";
        }
    }

    @ModelAttribute("user")
    public UserForm user() {
        return new UserForm();
    }
}
