package com.fundzforus.server.dao.mybatis;

import com.fundzforus.server.domain.Tenant;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TenantMapper {
    List<Tenant> findAllTenants();

    Tenant findTenantByTenantName(Map<String, String> map);

    Tenant getTenantById(int id);

    int insertTenant(Tenant tenant);

    int updateTenant(Tenant tenant);

    int deleteTenantById(Map<String, String> map);
}