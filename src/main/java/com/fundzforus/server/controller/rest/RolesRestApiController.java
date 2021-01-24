package com.fundzforus.server.controller.rest;

import com.fundzforus.server.domain.Response;
import com.fundzforus.server.domain.Role;
import com.fundzforus.server.service.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
public class RolesRestApiController {

    @Autowired
    private IRoleService roleServiceImpl;

    @GetMapping(value = "/rest/getAllRoles", produces = "application/json")
    public List<Role> getAllUsers() {
        return roleServiceImpl.findAllRoles();
    }

    @PostMapping(value = "/rest/role", produces = "application/json", consumes = "application/json")
    public Response createRole(@RequestBody Role role) {
        int rowsInserted = roleServiceImpl.createRole(role);
        if (rowsInserted == 1) {
            return new Response("success", LocalDateTime.now());
        } else {
            return new Response("failed", LocalDateTime.now());
        }
    }

    @PutMapping(value = "/rest/role", produces = "application/json")
    public Response updateRole(@RequestBody Role role) {
        int rowsUpdated = roleServiceImpl.updateRole(role);
        if (rowsUpdated == 1) {
            return new Response("success", LocalDateTime.now());
        } else {
            return new Response("failed", LocalDateTime.now());
        }
    }

    @GetMapping(value = "/rest/role", produces = "application/json")
    public Role getRoleById(@RequestHeader String roleId) {
        Role response = roleServiceImpl.getById(Integer.parseInt(roleId));
        return response;
    }

    @DeleteMapping(value = "/rest/role", produces = "application/json")
    public Response deleteRoleById(@RequestHeader String roleId) {
        int rowsDeleted = roleServiceImpl.deleteRoleByName(roleId);
        if (rowsDeleted == 1) {
            return new Response("success", LocalDateTime.now());
        } else {
            return new Response("failed", LocalDateTime.now());
        }
    }

    @GetMapping(value = "/rest/roleByName", produces = "application/json")
    public Role getRoleByName(@RequestHeader String roleName) {
        Role response = roleServiceImpl.findRoleWithRoleName(roleName);
        return response;
    }

    @DeleteMapping(value = "/rest/roleByName", produces = "application/json")
    public Response deleteRoleByName(@RequestHeader String roleName) {
        int rowsDeleted = roleServiceImpl.deleteRoleByName(roleName);
        if (rowsDeleted == 1) {
            return new Response("success", LocalDateTime.now());
        } else {
            return new Response("failed", LocalDateTime.now());
        }
    }
}