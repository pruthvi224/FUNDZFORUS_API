package com.fundzforus.server.dao.mybatis;

import com.fundzforus.server.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    List<User> findAllUsers();

    User findUserByEmailAndPassword(User user);

    User findUserWithEmail(Map<String, String> map);

    User getById(Map<String, String> map);

    int insertUser(User user);

    int updateUser(User user);

    int updatePassword(User user);

    int deleteUserById(Map<String, String> map);
}
