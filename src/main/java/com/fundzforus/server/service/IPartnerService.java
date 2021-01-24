package com.fundzforus.server.service;

import com.fundzforus.server.domain.Partner;
import com.fundzforus.server.domain.PartnerImage;
import com.fundzforus.server.domain.PartnerVideo;

import java.util.List;

public interface IPartnerService {
    List<Partner> findAllPartners();

    Partner findPartnerWithPartnerName(String partnerName);

    Partner getPartnerById(int id);

    int createPartner(Partner partner);

    int updatePartner(Partner partner);

    int deletePartnerByPartnerName(String partnerName);

    List<PartnerVideo> findAllPartnerVideos();

    List<PartnerVideo> findPartnerVideosByPartnerId(int partnerId);

    PartnerVideo getPartnerVideoById(int id);

    int createPartnerVideo(PartnerVideo partner);

    int updatePartnerVideo(PartnerVideo partner);

    int deletePartnerVideoByPartnerId(int partnerId);

    List<PartnerImage> findAllPartnerImages();

    List<PartnerImage> findPartnerImagesByPartnerId(int partnerId);

    PartnerImage getPartnerImageById(int id);

    int createPartnerImage(PartnerImage partner);

    int updatePartnerImage(PartnerImage partner);

    int deletePartnerImageByPartnerId(int partnerId);
}
