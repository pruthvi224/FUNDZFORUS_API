package com.fundzforus.server.service;

import com.fundzforus.server.dao.mybatis.RoleMapper;
import com.fundzforus.server.domain.Role;
import com.fundzforus.server.exception.MissingMandatoryFieldsException;
import com.fundzforus.server.exception.RoleAlreadyExistException;
import com.fundzforus.server.exception.RoleNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

;

@Component
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> findAllRoles() {
        return roleMapper.findAllRoles();
    }

    @Override
    public Role findRoleWithRoleName(String roleName) {
        Map parameterMap = new HashMap();
        parameterMap.put("roleName", roleName);
        return roleMapper.findRoleByRoleName(parameterMap);
    }

    @Override
    public Role getById(int id) {
        return null;
    }

    @Override
    public int createRole(Role role) {
        if (StringUtils.isBlank(role.getRoleName())) {
            new MissingMandatoryFieldsException("Role Name can not be Empty");
        }

        if (role.getRoleName() != null) {
            Map parameterMap = new HashMap();
            parameterMap.put("roleName", role.getRoleName());
            Role dbRole = roleMapper.findRoleByRoleName(parameterMap);
            if (dbRole != null) {
                throw new RoleAlreadyExistException("Role ::" + role.getRoleName() + " with same role name already exist");
            }
            return roleMapper.insertRole(role);
        }
        return 0;
    }

    @Override
    public int updateRole(Role role) {
        if (StringUtils.isBlank(role.getRoleName())) {
            new MissingMandatoryFieldsException("Role Name can not be Empty");
        }

        if (role.getRoleName() != null) {
            Map parameterMap = new HashMap();
            parameterMap.put("roleName", role.getRoleName());
            Role dbRole = roleMapper.findRoleByRoleName(parameterMap);
            if (dbRole == null) {
                throw new RoleNotFoundException("Role ::" + role.getRoleName() + " does not exist");
            }
            role.setId(dbRole.getId());
            return roleMapper.updateRole(role);
        }
        return 0;
    }

    @Override
    public int deleteRoleByName(String roleName) {
        if (StringUtils.isBlank(roleName)) {
            new MissingMandatoryFieldsException("RoleName can not be Empty");
        }

        Map parameterMap = new HashMap();
        parameterMap.put("roleName", roleName);
        Role dbRole = roleMapper.findRoleByRoleName(parameterMap);
        if (dbRole != null) {
            parameterMap.put("id", dbRole.getId());
            return roleMapper.deleteRoleById(parameterMap);
        } else {
            throw new RoleNotFoundException("Role ::" + roleName + " does not exist");
        }
    }

    @Override
    public int deleteRoleById(int roleId) {
        Role dbRole = roleMapper.getRoleById(roleId);
        if (dbRole != null) {
            Map parameterMap = new HashMap();
            parameterMap.put("id", dbRole.getId());
            return roleMapper.deleteRoleById(parameterMap);
        } else {
            throw new RoleNotFoundException("Role ::" + roleId + " does not exist");
        }
    }
}
