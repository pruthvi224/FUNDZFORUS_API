package com.fundzforus.server.service;

import com.fundzforus.server.domain.UserVideo;

import java.util.List;

public interface IUserVideoService {
    List<UserVideo> findAllUserVideosByTenantId(int tenantId);

    UserVideo getUserVideoById(int id);

    int createUserVideo(UserVideo userVideo);

    int updateUserVideo(UserVideo userVideo);

    int deleteUserVideo(int id);
}