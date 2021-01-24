package com.fundzforus.server.service;

import com.fundzforus.server.domain.Tenant;

import java.util.List;

public interface ITenantService {
    List<Tenant> findAllTenants();

    Tenant findTenantWithTenantName(String tenantName);

    Tenant getById(int id);

    int createTenant(Tenant tenant);

    int updateTenant(Tenant tenant);

    int deleteTenantByTenantName(String tenantName);

    int deleteTenantById(int id);
}
