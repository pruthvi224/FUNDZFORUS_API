package com.fundzforus.server.dao.mybatis;

import com.fundzforus.server.domain.PartnerImage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PartnerImageMapper {
    List<PartnerImage> findAllPartnerImages();

    List<PartnerImage> findPartnerImagesByPartnerId(Map<String, String> map);

    PartnerImage getPartnerImageById(Map<String, String> map);

    int insertPartnerImage(PartnerImage partner);

    int updatePartnerImage(PartnerImage partner);

    int deletePartnerImageById(Map<String, String> map);
}
