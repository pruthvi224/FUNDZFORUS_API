package com.fundzforus.server.dao.mybatis;

import com.fundzforus.server.domain.PartnerVideo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PartnerVideoMapper {
    List<PartnerVideo> findAllPartnerVideos();

    List<PartnerVideo> findPartnerVideosByPartnerId(Map<String, String> map);

    PartnerVideo getPartnerVideoById(Map<String, String> map);

    int insertPartnerVideo(PartnerVideo partner);

    int updatePartnerVideo(PartnerVideo partner);

    int deletePartnerVideoById(Map<String, String> map);
}
