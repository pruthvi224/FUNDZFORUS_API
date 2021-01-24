package com.fundzforus.server.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Partner {
    private int id;
    private String partnerName;
    private String description;
    private String logoURL;
    private String contactName;
    private String contactPhone;
    private String contactAddress;
    private String contactEmail;
    private String organizationName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDateTime;
    private String createdBy;
    private String updatedBy;
}