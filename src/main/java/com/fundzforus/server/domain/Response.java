package com.fundzforus.server.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Response {
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timeStamp;
    private String message;

    public Response(String status, LocalDateTime timeStamp) {
        this.status = status;
        this.timeStamp = timeStamp;
    }
}
