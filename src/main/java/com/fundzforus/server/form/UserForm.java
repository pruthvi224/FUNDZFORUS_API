package com.fundzforus.server.form;

import com.fundzforus.server.domain.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Data
public class UserForm {
    private int userId;
    private boolean loggedIn;
    private String message;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String location;
    private MultipartFile multiPartImage;
    private String imageUrl;
    private String userTenantId;
    private boolean receiveNotification;
    private List<Partner> partners;
    private List<List<Partner>> partnersList;
    private Partner partner;
    private PartnerImage newPartnerImage;
    private List<PartnerImage> partnerImages;
    private List<List<PartnerImage>> partnerImagesList;
    private List<PartnerVideo> partnerVideos;
    private Partner newPartner;
    private PartnerVideo newPartnerVideo;

    private List<Program> programs;
    private Program program;
    private Program newProgram;
    private ProgramBooking programBooking;

    private List<Message> messages;
    private Message newMessage;

    private List<UserVideo> userVideos;
    private UserVideo newVideo;
    
    private List<NewsReader> newsReaderList;
    private List<List<NewsReader>> newsReaderSubList;
    private NewsReader newsReaderNew;
    private NewsReader newsReader;

    private List<Tenant> tenants;

    private boolean firstVideoExist;
    private boolean secondVideoExist;
    private boolean thirdVideoExist;
}
