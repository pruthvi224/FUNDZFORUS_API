package com.fundzforus.server.controller.rest;

import com.fundzforus.server.domain.Response;
import com.fundzforus.server.domain.UserVideo;
import com.fundzforus.server.exception.MissingMandatoryFieldsException;
import com.fundzforus.server.exception.UserVideoAlreadyExistException;
import com.fundzforus.server.exception.UserVideoNotFoundException;
import com.fundzforus.server.service.IUserVideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
public class UserVideosRestApiController {

    @Autowired
    private IUserVideoService videoServiceImpl;

    @GetMapping(value = "/rest/allUserVideosByTenantId", produces = "application/json")
    public List<UserVideo> getAllVideos(@RequestHeader String tenantId) {
        return videoServiceImpl.findAllUserVideosByTenantId(Integer.parseInt(tenantId));
    }

    @PostMapping(value = "/rest/video", produces = "application/json", consumes = "application/json")
    public Response createVideo(@RequestBody UserVideo userVideo) {
        int rowsInserted = 0;
        try {
            rowsInserted = videoServiceImpl.createUserVideo(userVideo);
        } catch (UserVideoAlreadyExistException | MissingMandatoryFieldsException e1) {
            return new Response("failed", LocalDateTime.now(), e1.getMessage());
        }
        if (rowsInserted == 1) {
            return new Response("success", LocalDateTime.now());
        } else {
            return new Response("failed", LocalDateTime.now());
        }
    }

    @PutMapping(value = "/rest/video", produces = "application/json")
    public Response updateVideo(@RequestBody UserVideo userVideo) {
        int rowsUpdated = 0;
        try {
            rowsUpdated = videoServiceImpl.updateUserVideo(userVideo);
        } catch (UserVideoNotFoundException | MissingMandatoryFieldsException e1) {
            return new Response("failed", LocalDateTime.now(), e1.getMessage());
        }
        if (rowsUpdated == 1) {
            return new Response("success", LocalDateTime.now());
        } else {
            return new Response("failed", LocalDateTime.now());
        }
    }

    @GetMapping(value = "/rest/video", produces = "application/json")
    public UserVideo getVideo(@RequestHeader String videoId) {
        UserVideo response = videoServiceImpl.getUserVideoById(Integer.parseInt(videoId));
        return response;
    }

    @DeleteMapping(value = "/rest/video", produces = "application/json")
    public Response deleteVideo(@RequestHeader String videoId) {
        int rowsDeleted = 0;
        try {
            rowsDeleted = videoServiceImpl.deleteUserVideo(Integer.parseInt(videoId));
        } catch (UserVideoNotFoundException | MissingMandatoryFieldsException e1) {
            return new Response("failed", LocalDateTime.now(), e1.getMessage());
        }
        if (rowsDeleted == 1) {
            return new Response("success", LocalDateTime.now());
        } else {
            return new Response("failed", LocalDateTime.now());
        }
    }
}
