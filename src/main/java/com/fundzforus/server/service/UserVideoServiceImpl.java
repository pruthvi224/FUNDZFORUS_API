package com.fundzforus.server.service;

import com.fundzforus.server.dao.mybatis.UserVideoMapper;
import com.fundzforus.server.domain.UserVideo;
import com.fundzforus.server.exception.MissingMandatoryFieldsException;
import com.fundzforus.server.exception.UserVideoAlreadyExistException;
import com.fundzforus.server.exception.UserVideoNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserVideoServiceImpl implements IUserVideoService {

    @Autowired
    private UserVideoMapper userVideoMapper;

    @Override
    public List<UserVideo> findAllUserVideosByTenantId(int tenantId) {
        Map parameterMap = new HashMap();
        parameterMap.put("tenantId", tenantId);
        return userVideoMapper.findAllUserVideosByTenantId(parameterMap);
    }

    @Override
    public UserVideo getUserVideoById(int id) {
        Map parameterMap = new HashMap();
        parameterMap.put("id", id);
        return userVideoMapper.getUserVideoById(parameterMap);
    }

    @Override
    public int createUserVideo(UserVideo userVideo) {
        if (StringUtils.isBlank(userVideo.getVideoName()) || userVideo.getVideoDescription() == null ||
                StringUtils.isBlank(userVideo.getVideoURL()) || StringUtils.isBlank(userVideo.getTenantId())) {
            throw new MissingMandatoryFieldsException("Video Name, Description, URL and UserId can not be Empty");
        } else {
            Map parameterMap = new HashMap();
            parameterMap.put("videoName", userVideo.getVideoName());
            parameterMap.put("tenantId", userVideo.getTenantId());
            UserVideo dbUserVideo = userVideoMapper.findUserVideoByNameAndTenantId(parameterMap);
            if (dbUserVideo != null) {
                throw new UserVideoAlreadyExistException("UserVideo With Name ::" + userVideo.getVideoName() + " already exist");
            }
            userVideo.setCreatedBy("MOBILE_APP");
            userVideo.setUpdatedBy("MOBILE_APP");
            return userVideoMapper.insertUserVideo(userVideo);
        }
    }

    @Override
    public int updateUserVideo(UserVideo userVideo) {
        if (StringUtils.isBlank(userVideo.getVideoName()) || userVideo.getVideoDescription() == null ||
                StringUtils.isBlank(userVideo.getVideoURL()) || StringUtils.isBlank(userVideo.getTenantId())) {
            throw new MissingMandatoryFieldsException("Video Name, Description, URL and UserId can not be Empty");
        } else {
            Map parameterMap = new HashMap();
            parameterMap.put("videoName", userVideo.getVideoName());
            UserVideo dbUserVideo = userVideoMapper.findUserVideoByNameAndTenantId(parameterMap);
            if (dbUserVideo == null) {
                throw new UserVideoNotFoundException("UserVideo With Name ::" + userVideo.getVideoName() + " does not exist");
            }
            userVideo.setId(dbUserVideo.getId());
            userVideo.setCreatedBy("MOBILE_APP");
            userVideo.setUpdatedBy("MOBILE_APP");
            return userVideoMapper.updateUserVideo(userVideo);
        }
    }

    @Override
    public int deleteUserVideo(int videoId) {
        if (videoId == 0) {
            throw new MissingMandatoryFieldsException("User Video Id can not be Empty or Zero");
        }

        Map parameterMap = new HashMap();
        parameterMap.put("id", videoId);
        UserVideo dbUserVideo = userVideoMapper.getUserVideoById(parameterMap);
        if (dbUserVideo != null) {
            parameterMap.put("id", dbUserVideo.getId());
            return userVideoMapper.deleteUserVideoById(parameterMap);
        } else {
            throw new UserVideoNotFoundException("UserVideo with Id ::" + videoId + " does not exist");
        }
    }
}
