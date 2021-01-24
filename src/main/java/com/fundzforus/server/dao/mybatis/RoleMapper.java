package com.fundzforus.server.dao.mybatis;

import com.fundzforus.server.domain.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface RoleMapper {
    List<Role> findAllRoles();

    Role findRoleByRoleName(Map<String, String> map);

    Role getRoleById(int id);

    int insertRole(Role role);

    int updateRole(Role role);

    int deleteRoleById(Map<String, String> map);
}