package com.fundzforus.server.dao.mybatis;

import com.fundzforus.server.domain.Partner;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PartnerMapper {
    List<Partner> findAllPartners();

    Partner findPartnerWithPartnerName(Map<String, String> map);

    Partner getPartnerById(Map<String, String> map);

    int insertPartner(Partner partner);

    int updatePartner(Partner partner);

    int deletePartnerById(Map<String, String> map);
}
