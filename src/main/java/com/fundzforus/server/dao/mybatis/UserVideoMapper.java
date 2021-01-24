package com.fundzforus.server.dao.mybatis;

import com.fundzforus.server.domain.UserVideo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserVideoMapper {
    List<UserVideo> findAllUserVideosByTenantId(Map<String, String> map);

    UserVideo findUserVideoByNameAndTenantId(Map<String, String> map);

    UserVideo getUserVideoById(Map<String, String> map);

    int insertUserVideo(UserVideo userVideo);

    int updateUserVideo(UserVideo userVideo);

    int deleteUserVideoById(Map<String, String> map);
}
