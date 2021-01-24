package com.fundzforus.server.service;

import com.fundzforus.server.dao.mybatis.TenantMapper;
import com.fundzforus.server.domain.Tenant;
import com.fundzforus.server.exception.MissingMandatoryFieldsException;
import com.fundzforus.server.exception.TenantAlreadyExistException;
import com.fundzforus.server.exception.TenantNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

;

@Component
public class TenantServiceImpl implements ITenantService {
    @Autowired
    private TenantMapper tenantMapper;

    @Override
    public List<Tenant> findAllTenants() {
        return tenantMapper.findAllTenants();
    }

    @Override
    public Tenant findTenantWithTenantName(String tenantName) {
        Map parameterMap = new HashMap();
        parameterMap.put("tenantName", tenantName);
        return tenantMapper.findTenantByTenantName(parameterMap);
    }

    @Override
    public Tenant getById(int id) {
        return tenantMapper.getTenantById(id);
    }

    @Override
    public int createTenant(Tenant tenant) {
        if (StringUtils.isBlank(tenant.getTenantName())) {
            new MissingMandatoryFieldsException("Tenant Name can not be Empty");
        }

        if (tenant.getTenantName() != null) {
            Map parameterMap = new HashMap();
            parameterMap.put("tenantName", tenant.getTenantName());
            Tenant dbTenant = tenantMapper.findTenantByTenantName(parameterMap);
            if (dbTenant != null) {
                throw new TenantAlreadyExistException("Tenant ::" + tenant.getTenantName() + " with same tenant name already exist");
            }
            return tenantMapper.insertTenant(tenant);
        }
        return 0;
    }

    @Override
    public int updateTenant(Tenant tenant) {
        if (StringUtils.isBlank(tenant.getTenantName())) {
            new MissingMandatoryFieldsException("Tenant Name can not be Empty");
        }

        if (tenant.getTenantName() != null) {
            Map parameterMap = new HashMap();
            parameterMap.put("tenantName", tenant.getTenantName());
            Tenant dbTenant = tenantMapper.findTenantByTenantName(parameterMap);
            if (dbTenant == null) {
                throw new TenantNotFoundException("Tenant ::" + tenant.getTenantName() + " does not exist");
            }
            tenant.setId(dbTenant.getId());
            return tenantMapper.updateTenant(tenant);
        }
        return 0;
    }

    @Override
    public int deleteTenantByTenantName(String tenantName) {
        if (StringUtils.isBlank(tenantName)) {
            new MissingMandatoryFieldsException("TenantName can not be Empty");
        }

        Map parameterMap = new HashMap();
        parameterMap.put("tenantName", tenantName);
        Tenant dbTenant = tenantMapper.findTenantByTenantName(parameterMap);
        if (dbTenant != null) {
            parameterMap.put("id", dbTenant.getId());
            return tenantMapper.deleteTenantById(parameterMap);
        } else {
            throw new TenantNotFoundException("Tenant ::" + tenantName + " does not exist");
        }
    }

    @Override
    public int deleteTenantById(int id) {
        Tenant dbTenant = tenantMapper.getTenantById(id);
        if (dbTenant != null) {
            Map parameterMap = new HashMap();
            parameterMap.put("id", dbTenant.getId());
            return tenantMapper.deleteTenantById(parameterMap);
        } else {
            throw new TenantNotFoundException("Tenant Id ::" + id + " does not exist");
        }
    }
}
