package com.fundzforus.server.service;

import com.fundzforus.server.dao.mybatis.RoleMapper;
import com.fundzforus.server.dao.mybatis.TenantMapper;
import com.fundzforus.server.dao.mybatis.UserMapper;
import com.fundzforus.server.domain.Role;
import com.fundzforus.server.domain.Tenant;
import com.fundzforus.server.domain.User;
import com.fundzforus.server.exception.MissingMandatoryFieldsException;
import com.fundzforus.server.exception.RoleNotFoundException;
import com.fundzforus.server.exception.UserAlreadyExistException;
import com.fundzforus.server.exception.UserNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserServiceImpl implements IUserService {

    public static final String DD_MMM_YY_HH_MM = "dd-MMM-yy HH:mm";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private TenantMapper tenantMapper;

    @Override
    public List<User> findAllUsers() {
        return userMapper.findAllUsers();
    }

    public User findUserWithEmailAndPassword(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userMapper.findUserByEmailAndPassword(user);
    }

    public User findUserWithEmail(String email) {
        Map parameterMap = new HashMap();
        parameterMap.put("email", email);
        return userMapper.findUserWithEmail(parameterMap);
    }

    public int deleteUser(String email) {
        if (StringUtils.isBlank(email)) {
            new MissingMandatoryFieldsException("Email can not be Empty");
        }

        Map parameterMap = new HashMap();
        parameterMap.put("email", email);
        User dbUser = userMapper.findUserWithEmail(parameterMap);
        if (dbUser != null) {
            parameterMap.put("id", dbUser.getId());
            return userMapper.deleteUserById(parameterMap);
        } else {
            throw new UserNotFoundException("User ::" + email + " does not exist");
        }
    }

    @Override
    public int createUser(User user) {
        if (StringUtils.isBlank(user.getEmail()) || StringUtils.isBlank(user.getPassword())) {
            new MissingMandatoryFieldsException("UserName and Password can not be Empty");
        }

        if (user.getEmail() != null) {
            Map parameterMap = new HashMap();
            parameterMap.put("email", user.getEmail());
            User dbUser = userMapper.findUserWithEmail(parameterMap);
            if (dbUser != null) {
                throw new UserAlreadyExistException("User ::" + user.getEmail() + " with same user name already exist");
            }
        }

        if (user.getRoleId() != null) {
            Role dbRole = roleMapper.getRoleById(user.getRoleId());
            if (dbRole == null) {
                throw new RoleNotFoundException("Role ::" + user.getRoleId() + " is not found");
            }
        }

        if (user.getTenantId() != null) {
            Tenant dbTenant = tenantMapper.getTenantById(user.getTenantId());
            if (dbTenant == null) {
                throw new UserNotFoundException("Tenant ::" + user.getTenantId() + " is not found");
            }
        }
        user.setCreatedBy("MOBILE_APP");
        user.setUpdatedBy("MOBILE_APP");
        return userMapper.insertUser(user);
    }

    @Override
    public int updateUser(User user) {
        User dbUser = null;

        if (StringUtils.isBlank(user.getEmail()) || StringUtils.isBlank(user.getPassword())) {
            new MissingMandatoryFieldsException("Email and Password can not be Empty");
        }

        if (user.getEmail() != null) {
            Map parameterMap = new HashMap();
            parameterMap.put("email", user.getEmail());
            dbUser = userMapper.findUserWithEmail(parameterMap);
            if (dbUser == null) {
                throw new UserNotFoundException("User ::" + user.getEmail() + " does not exist");
            }
        }

        if (user.getRoleId() != null) {
            Role dbRole = roleMapper.getRoleById(user.getRoleId());
            if (dbRole == null) {
                throw new RoleNotFoundException("Role ::" + user.getRoleId() + " is not found");
            }
        }

        if (user.getTenantId() != null) {
            Tenant dbTenant = tenantMapper.getTenantById(user.getTenantId());
            if (dbTenant == null) {
                throw new UserNotFoundException("Tenant ::" + user.getTenantId() + " is not found");
            }
        }
        user.setId(dbUser.getId());
        user.setUpdatedBy("MOBILE_APP");
        return userMapper.updateUser(user);
    }


}
