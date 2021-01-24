package com.fundzforus.server.controller.rest;

import com.fundzforus.server.domain.Response;
import com.fundzforus.server.domain.User;
import com.fundzforus.server.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
public class UserRestApiController {

    @Autowired
    private IUserService userServiceImpl;

    @GetMapping(value = "/rest/getAllUsers", produces = "application/json")
    public List<User> getAllUsers() {
        return userServiceImpl.findAllUsers();
    }

    @PostMapping(value = "/rest/user", produces = "application/json", consumes = "application/json")
    public Response createUser(@RequestBody User user) {
        int rowsInserted = userServiceImpl.createUser(user);
        if (rowsInserted == 1) {
            return new Response("success", LocalDateTime.now());
        } else {
            return new Response("failed", LocalDateTime.now());
        }
    }

    @PutMapping(value = "/rest/user", produces = "application/json")
    public Response updateUser(@RequestBody User user) {
        int rowsUpdated = userServiceImpl.updateUser(user);
        if (rowsUpdated == 1) {
            return new Response("success", LocalDateTime.now());
        } else {
            return new Response("failed", LocalDateTime.now());
        }
    }

    @GetMapping(value = "/rest/user", produces = "application/json")
    public User getUser(@RequestHeader String userName, @RequestHeader String password) {
        User response = userServiceImpl.findUserWithEmailAndPassword(userName, password);
        return response;
    }

    @DeleteMapping(value = "/rest/user", produces = "application/json")
    public Response deleteUser(@RequestHeader String userName) {
        int rowsDeleted = userServiceImpl.deleteUser(userName);
        if (rowsDeleted == 1) {
            return new Response("success", LocalDateTime.now());
        } else {
            return new Response("failed", LocalDateTime.now());
        }
    }
}