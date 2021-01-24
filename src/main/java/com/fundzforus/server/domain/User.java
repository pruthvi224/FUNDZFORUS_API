package com.fundzforus.server.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Integer id;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Integer roleId;
    private Integer tenantId;
    private boolean receiveNotification;
    private String location;
    private String imageUrl;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDateTime;
    private String createdBy;
    private String updatedBy;

    public boolean getReceiveNotification() {
        return receiveNotification;
    }
}