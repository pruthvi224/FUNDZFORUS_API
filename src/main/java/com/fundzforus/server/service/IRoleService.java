package com.fundzforus.server.service;

import com.fundzforus.server.domain.Role;

import java.util.List;

public interface IRoleService {
    List<Role> findAllRoles();

    Role findRoleWithRoleName(String roleName);

    Role getById(int id);

    int createRole(Role role);

    int updateRole(Role role);

    int deleteRoleByName(String roleName);

    int deleteRoleById(int roleId);
}
