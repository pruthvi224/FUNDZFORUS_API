package com.fundzforus.server.service;

import com.fundzforus.server.domain.User;

import java.util.List;

public interface IUserService {

    List<User> findAllUsers();

    User findUserWithEmailAndPassword(String email, String password);

    User findUserWithEmail(String email);

    int createUser(User user);

    int updateUser(User user);

    int deleteUser(String userName);
}
