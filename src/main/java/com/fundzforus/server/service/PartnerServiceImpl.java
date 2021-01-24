package com.fundzforus.server.service;

import com.fundzforus.server.dao.mybatis.PartnerImageMapper;
import com.fundzforus.server.dao.mybatis.PartnerMapper;
import com.fundzforus.server.dao.mybatis.PartnerVideoMapper;
import com.fundzforus.server.domain.Partner;
import com.fundzforus.server.domain.PartnerImage;
import com.fundzforus.server.domain.PartnerVideo;
import com.fundzforus.server.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class PartnerServiceImpl implements IPartnerService {
    @Autowired
    private PartnerMapper partnerMapper;

    @Autowired
    private PartnerVideoMapper partnerVideoMapper;

    @Autowired
    private PartnerImageMapper partnerImageMapper;

    @Override
    public List<Partner> findAllPartners() {
        return partnerMapper.findAllPartners();
    }

    @Override
    public Partner findPartnerWithPartnerName(String partnerName) {
        Map parameterMap = new HashMap();
        parameterMap.put("partnerName", partnerName);
        return partnerMapper.findPartnerWithPartnerName(parameterMap);
    }

    @Override
    public Partner getPartnerById(int id) {
        Map parameterMap = new HashMap();
        parameterMap.put("id", id);
        return partnerMapper.getPartnerById(parameterMap);
    }

    @Override
    public int createPartner(Partner partner) {
        if (StringUtils.isBlank(partner.getPartnerName()) || StringUtils.isBlank(partner.getContactName()) ||
                StringUtils.isBlank(partner.getContactPhone()) || StringUtils.isBlank(partner.getContactAddress()) ||
                StringUtils.isBlank(partner.getContactEmail()) || StringUtils.isBlank(partner.getOrganizationName()) ||
                StringUtils.isBlank(partner.getLogoURL())) {
            throw new MissingMandatoryFieldsException("PartnerName, OrganizationName, ContactName, ContactPhone, " +
                    "ContactAddress, ContactEail and PartnerLogo can not be Empty");
        } else if (StringUtils.isNotBlank(partner.getPartnerName())) {
            Map parameterMap = new HashMap();
            parameterMap.put("partnerName", partner.getPartnerName());
            Partner dbPartner = partnerMapper.findPartnerWithPartnerName(parameterMap);
            if (dbPartner != null) {
                throw new PartnerAlreadyExistException("Partner ::" + partner.getPartnerName() + " already exist");
            }
            partner.setCreatedBy("MOBILE_APP");
            partner.setUpdatedBy("MOBILE_APP");
            return partnerMapper.insertPartner(partner);
        }
        return 0;
    }

    @Override
    public int updatePartner(Partner partner) {
        if (StringUtils.isBlank(partner.getPartnerName()) || StringUtils.isBlank(partner.getContactName()) ||
                StringUtils.isBlank(partner.getContactPhone()) || StringUtils.isBlank(partner.getContactAddress()) ||
                StringUtils.isBlank(partner.getContactEmail())) {
            throw new MissingMandatoryFieldsException("PartnerName, ContactName, ContactPhone, ContactAddress and ContactEmail can not be Empty");
        } else if (StringUtils.isNotBlank(partner.getPartnerName())) {
            Map parameterMap = new HashMap();
            parameterMap.put("partnerName", partner.getPartnerName());
            Partner dbPartner = partnerMapper.findPartnerWithPartnerName(parameterMap);
            if (dbPartner == null) {
                throw new PartnerNotFoundException("Partner ::" + partner.getPartnerName() + " does not exist");
            }
            partner.setUpdatedBy("MOBILE_APP");
            partner.setId(dbPartner.getId());
            return partnerMapper.updatePartner(partner);
        }
        return 0;
    }

    @Override
    public int deletePartnerByPartnerName(String partnerName) {
        if (StringUtils.isBlank(partnerName)) {
            new MissingMandatoryFieldsException("Partner can not be Empty");
        }

        Map parameterMap = new HashMap();
        parameterMap.put("partnerName", partnerName);
        Partner dbPartner = partnerMapper.findPartnerWithPartnerName(parameterMap);
        if (dbPartner != null) {
            parameterMap.put("id", dbPartner.getId());
            return partnerMapper.deletePartnerById(parameterMap);
        } else {
            throw new PartnerNotFoundException("Partner ::" + partnerName + " does not exist");
        }
    }

    @Override
    public List<PartnerVideo> findAllPartnerVideos() {
        return partnerVideoMapper.findAllPartnerVideos();
    }

    @Override
    public List<PartnerVideo> findPartnerVideosByPartnerId(int partnerId) {
        Map parameterMap = new HashMap();
        parameterMap.put("partnerId", partnerId);
        List<PartnerVideo> dbPartnerVideoList = partnerVideoMapper.findPartnerVideosByPartnerId(parameterMap);
//        if (dbPartnerVideoList == null) {
//            throw new PartnerVideoNotFoundException("PartnerVideo for PartnerId ::" + partnerId + " does not exist");
//        }
        return dbPartnerVideoList;
    }

    @Override
    public PartnerVideo getPartnerVideoById(int id) {
        Map parameterMap = new HashMap();
        parameterMap.put("id", id);
        return partnerVideoMapper.getPartnerVideoById(parameterMap);
    }

    @Override
    public int createPartnerVideo(PartnerVideo partnerVideo) {
        if (StringUtils.isBlank(partnerVideo.getVideoName()) || StringUtils.isBlank(partnerVideo.getVideoDescription()) ||
                StringUtils.isBlank(partnerVideo.getVideoURL()) || StringUtils.isBlank(partnerVideo.getPartnerId())) {
            throw new MissingMandatoryFieldsException("PartnerVideo Name, Description, URL, and PartnerId can not be Empty");
        } else if (StringUtils.isNotBlank(partnerVideo.getPartnerId())) {
//            Map parameterMap = new HashMap();
//            parameterMap.put("partnerId", partnerVideo.getPartnerId());
//            List<PartnerVideo> dbPartnerVideo = partnerVideoMapper.findPartnerVideoByPartnerId(parameterMap);
//            if (dbPartnerVideo != null) {
//                throw new PartnerVideoAlreadyExistException("PartnerVideo for PartnerId ::" + partnerVideo.getPartnerId() + " already exist");
//            }
            partnerVideo.setCreatedBy("MOBILE_APP");
            partnerVideo.setUpdatedBy("MOBILE_APP");
            return partnerVideoMapper.insertPartnerVideo(partnerVideo);
        }
        return 0;
    }

    @Override
    public int updatePartnerVideo(PartnerVideo partnerVideo) {
        if (StringUtils.isBlank(partnerVideo.getVideoName()) || StringUtils.isBlank(partnerVideo.getVideoDescription()) ||
                StringUtils.isBlank(partnerVideo.getVideoURL()) || StringUtils.isBlank(partnerVideo.getPartnerId())) {
            throw new MissingMandatoryFieldsException("PartnerVideo Name, Description, URL, and PartnerId can not be Empty");
        } else if (StringUtils.isNotBlank(partnerVideo.getPartnerId())) {
//            Map parameterMap = new HashMap();
//            parameterMap.put("partnerId", partnerVideo.getPartnerId());
//            List<PartnerVideo> dbPartnerVideo = partnerVideoMapper.findPartnerVideoByPartnerId(parameterMap);
//            if (dbPartnerVideo == null) {
//                throw new PartnerVideoNotFoundException("PartnerVideo for Partner Id::" + partnerVideo.getPartnerId() + " does not exist");
//            }
//            partnerVideo.setId(dbPartnerVideo.getId());
            partnerVideo.setUpdatedBy("MOBILE_APP");
            return partnerVideoMapper.updatePartnerVideo(partnerVideo);
        }
        return 0;
    }

    @Override
    public int deletePartnerVideoByPartnerId(int partnerId) {
        if (partnerId == 0) {
            new MissingMandatoryFieldsException("Partner Id can not be Empty or Zero");
        }

        Map parameterMap = new HashMap();
        parameterMap.put("partnerId", partnerId);
        List<PartnerVideo> dbPartnerVideoList = partnerVideoMapper.findPartnerVideosByPartnerId(parameterMap);
        if (dbPartnerVideoList != null) {
            for (PartnerVideo dbPartnerVideo : dbPartnerVideoList) {
                parameterMap.put("id", dbPartnerVideo.getId());
                partnerVideoMapper.deletePartnerVideoById(parameterMap);
            }
            return 1;
        } else {
            throw new PartnerVideoNotFoundException("PartnerVideo for PartnerId ::" + partnerId + " does not exist");
        }
    }

    @Override
    public List<PartnerImage> findAllPartnerImages() {
        return partnerImageMapper.findAllPartnerImages();
    }

    @Override
    public List<PartnerImage> findPartnerImagesByPartnerId(int partnerId) {
        Map parameterMap = new HashMap();
        parameterMap.put("partnerId", partnerId);
        List<PartnerImage> dbPartnerImages = partnerImageMapper.findPartnerImagesByPartnerId(parameterMap);
        log.warn("Partner Images not found");
//        if (dbPartnerImages == null || dbPartnerImages.size() == 0) {
//            throw new PartnerImgNotFoundException(("PartnerImages for PartnerId ::" + partnerId + " does not exist");
//        }
        return dbPartnerImages;
    }

    @Override
    public PartnerImage getPartnerImageById(int id) {
        return null;
    }

    @Override
    public int createPartnerImage(PartnerImage partnerImage) {
        if (StringUtils.isBlank(partnerImage.getImgName()) || StringUtils.isBlank(partnerImage.getImgDescription()) ||
                StringUtils.isBlank(partnerImage.getImgURL()) || StringUtils.isBlank(partnerImage.getPartnerId())) {
            throw new MissingMandatoryFieldsException("PartnerImg Name, Description, URL, and PartnerId can not be Empty");
        } else if (StringUtils.isNotBlank(partnerImage.getPartnerId())) {
//            Map parameterMap = new HashMap();
//            parameterMap.put("partnerId", partnerImage.getPartnerId());
//            List<PartnerImage> dbPartnerImages = partnerImageMapper.findPartnerImageByPartnerId(parameterMap);
//            if (dbPartnerImages == null || dbPartnerImages.size() == 0) {
//                throw new PartnerImgAlreadyExistException("PartnerImg for Partner Id::" + partnerImage.getPartnerId() + " already exist");
//            }
            partnerImage.setCreatedBy("MOBILE_APP");
            partnerImage.setUpdatedBy("MOBILE_APP");
            return partnerImageMapper.insertPartnerImage(partnerImage);
        }
        return 0;
    }

    @Override
    public int updatePartnerImage(PartnerImage partnerImage) {
        if (StringUtils.isBlank(partnerImage.getImgName()) || StringUtils.isBlank(partnerImage.getImgDescription()) ||
                StringUtils.isBlank(partnerImage.getImgURL()) || StringUtils.isBlank(partnerImage.getPartnerId())) {
            throw new MissingMandatoryFieldsException("PartnerImg Name, Description, URL, and PartnerId can not be Empty");
        } else if (StringUtils.isNotBlank(partnerImage.getPartnerId())) {
//            Map parameterMap = new HashMap();
//            parameterMap.put("partnerId", partnerImage.getPartnerId());
//            List<PartnerImage> dbPartnerImages = partnerImageMapper.findPartnerImageByPartnerId(parameterMap);
//            if (dbPartnerImages == null) {
//                throw new PartnerImgNotFoundException("PartnerImg for Partner Id::" + partnerImage.getPartnerId() + " does not exist");
//            }
//            partnerImage.setId(dbPartnerImage.getId());
            partnerImage.setUpdatedBy("MOBILE_APP");
            return partnerImageMapper.updatePartnerImage(partnerImage);
        }
        return 0;
    }

    @Override
    public int deletePartnerImageByPartnerId(int partnerId) {
        if (partnerId == 0) {
            throw new MissingMandatoryFieldsException("Partner Id can not be Empty or Zero");
        }

        Map parameterMap = new HashMap();
        parameterMap.put("partnerId", partnerId);
        List<PartnerImage> dbPartnerImageList = partnerImageMapper.findPartnerImagesByPartnerId(parameterMap);
        if (dbPartnerImageList != null) {
            for (PartnerImage dbPartnerImage : dbPartnerImageList) {
                parameterMap.put("id", dbPartnerImage.getId());
                partnerImageMapper.deletePartnerImageById(parameterMap);
            }
            return 1;
        } else {
            throw new PartnerImgNotFoundException("PartnerImg for PartnerId ::" + partnerId + " does not exist");
        }
    }
}
