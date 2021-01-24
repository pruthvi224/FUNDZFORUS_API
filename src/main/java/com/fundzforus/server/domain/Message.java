package com.fundzforus.server.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Message {
    private int id;
    private String messageTitle;
    private String messageDescription;
    private MessageCategory messageCategory;
    private String tenantId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime createDateTime;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime updateDateTime;
    private String createdBy;
    private String updatedBy;
}