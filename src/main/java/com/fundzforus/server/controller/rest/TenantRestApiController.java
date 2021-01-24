package com.fundzforus.server.controller.rest;

import com.fundzforus.server.domain.Response;
import com.fundzforus.server.domain.Tenant;
import com.fundzforus.server.service.ITenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
public class TenantRestApiController {

    @Autowired
    private ITenantService tenantServiceImpl;

    @GetMapping(value = "/rest/getAllTenants", produces = "application/json")
    public List<Tenant> getAllUsers() {
        return tenantServiceImpl.findAllTenants();
    }

    @PostMapping(value = "/rest/tenant", produces = "application/json", consumes = "application/json")
    public Response createTenant(@RequestBody Tenant tenant) {
        int rowsInserted = tenantServiceImpl.createTenant(tenant);
        if (rowsInserted == 1) {
            return new Response("success", LocalDateTime.now());
        } else {
            return new Response("failed", LocalDateTime.now());
        }
    }

    @PutMapping(value = "/rest/tenant", produces = "application/json")
    public Response updateTenant(@RequestBody Tenant tenant) {
        int rowsUpdated = tenantServiceImpl.updateTenant(tenant);
        if (rowsUpdated == 1) {
            return new Response("success", LocalDateTime.now());
        } else {
            return new Response("failed", LocalDateTime.now());
        }
    }

    @GetMapping(value = "/rest/tenant", produces = "application/json")
    public Tenant getTenantById(@RequestHeader String tenantId) {
        Tenant response = tenantServiceImpl.getById(Integer.parseInt(tenantId));
        return response;
    }

    @DeleteMapping(value = "/rest/tenant", produces = "application/json")
    public Response deleteTenantById(@RequestHeader String tenantId) {
        int rowsDeleted = tenantServiceImpl.deleteTenantById(Integer.parseInt(tenantId));
        if (rowsDeleted == 1) {
            return new Response("success", LocalDateTime.now());
        } else {
            return new Response("failed", LocalDateTime.now());
        }
    }

    @GetMapping(value = "/rest/tenantByName", produces = "application/json")
    public Tenant getTenantByName(@RequestHeader String tenantName) {
        Tenant response = tenantServiceImpl.findTenantWithTenantName(tenantName);
        return response;
    }

    @DeleteMapping(value = "/rest/tenantByName", produces = "application/json")
    public Response deleteTenant(@RequestHeader String tenantName) {
        int rowsDeleted = tenantServiceImpl.deleteTenantByTenantName(tenantName);
        if (rowsDeleted == 1) {
            return new Response("success", LocalDateTime.now());
        } else {
            return new Response("failed", LocalDateTime.now());
        }
    }
}