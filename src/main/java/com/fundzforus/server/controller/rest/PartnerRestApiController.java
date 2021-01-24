package com.fundzforus.server.controller.rest;

import com.fundzforus.server.domain.Partner;
import com.fundzforus.server.domain.PartnerImage;
import com.fundzforus.server.domain.PartnerVideo;
import com.fundzforus.server.domain.Response;
import com.fundzforus.server.service.IPartnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
public class PartnerRestApiController {

    @Autowired
    private IPartnerService partnerServiceImpl;

    @GetMapping(value = "/rest/getAllPartners", produces = "application/json")
    public List<Partner> getAllPartners() {
        return partnerServiceImpl.findAllPartners();
    }

    @GetMapping(value = "/rest/getAllPartnerImages", produces = "application/json")
    public List<PartnerImage> getAllPartnerImages() {
        return partnerServiceImpl.findAllPartnerImages();
    }

    @GetMapping(value = "/rest/getAllPartnerVideos", produces = "application/json")
    public List<PartnerVideo> getAllPartnerVideos() {
        return partnerServiceImpl.findAllPartnerVideos();
    }

    @PostMapping(value = "/rest/partner", produces = "application/json", consumes = "application/json")
    public Response createPartner(@RequestBody Partner partner) {
        int rowsInserted = partnerServiceImpl.createPartner(partner);
        if (rowsInserted == 1) {
            return new Response("success", LocalDateTime.now());
        } else {
            return new Response("failed", LocalDateTime.now());
        }
    }

    @PostMapping(value = "/rest/partnerVideo", produces = "application/json", consumes = "application/json")
    public Response createPartnerVideo(@RequestBody PartnerVideo partnerImage) {
        int rowsInserted = partnerServiceImpl.createPartnerVideo(partnerImage);
        if (rowsInserted == 1) {
            return new Response("success", LocalDateTime.now());
        } else {
            return new Response("failed", LocalDateTime.now());
        }
    }

    @PostMapping(value = "/rest/partnerImage", produces = "application/json", consumes = "application/json")
    public Response createPartnerImage(@RequestBody PartnerImage partnerImage) {
        int rowsInserted = partnerServiceImpl.createPartnerImage(partnerImage);
        if (rowsInserted == 1) {
            return new Response("success", LocalDateTime.now());
        } else {
            return new Response("failed", LocalDateTime.now());
        }
    }

    @PutMapping(value = "/rest/partner", produces = "application/json")
    public Response updatePartner(@RequestBody Partner partner) {
        int rowsUpdated = partnerServiceImpl.updatePartner(partner);
        if (rowsUpdated == 1) {
            return new Response("success", LocalDateTime.now());
        } else {
            return new Response("failed", LocalDateTime.now());
        }
    }

    @PutMapping(value = "/rest/partnerVideo", produces = "application/json")
    public Response updatePartnerVideo(@RequestBody PartnerVideo partnerVideo) {
        int rowsUpdated = partnerServiceImpl.updatePartnerVideo(partnerVideo);
        if (rowsUpdated == 1) {
            return new Response("success", LocalDateTime.now());
        } else {
            return new Response("failed", LocalDateTime.now());
        }
    }

    @PutMapping(value = "/rest/partnerImage", produces = "application/json")
    public Response updatePartnerImage(@RequestBody PartnerImage partnerImage) {
        int rowsUpdated = partnerServiceImpl.updatePartnerImage(partnerImage);
        if (rowsUpdated == 1) {
            return new Response("success", LocalDateTime.now());
        } else {
            return new Response("failed", LocalDateTime.now());
        }
    }

    @GetMapping(value = "/rest/partner/id", produces = "application/json")
    public Partner getPartnerById(@RequestHeader String partnerId) {
        Partner response = partnerServiceImpl.getPartnerById(Integer.parseInt(partnerId));
        return response;
    }

    @GetMapping(value = "/rest/partner/name", produces = "application/json")
    public Partner getPartnerByPartnerName(@RequestHeader String partnerName) {
        Partner response = partnerServiceImpl.findPartnerWithPartnerName(partnerName);
        return response;
    }

    @GetMapping(value = "/rest/partnerVideo", produces = "application/json")
    public List<PartnerVideo> getPartnerVideo(@RequestHeader String partnerId) {
        List<PartnerVideo> response = partnerServiceImpl.findPartnerVideosByPartnerId(Integer.parseInt(partnerId));
        return response;
    }

    @GetMapping(value = "/rest/partnerImage", produces = "application/json")
    public List<PartnerImage> getPartnerImage(@RequestHeader String partnerId) {
        List<PartnerImage> response = partnerServiceImpl.findPartnerImagesByPartnerId(Integer.parseInt(partnerId));
        return response;
    }

    @DeleteMapping(value = "/rest/partner/name", produces = "application/json")
    public Response deletePartner(@RequestHeader String partnerName) {
        int rowsDeleted = partnerServiceImpl.deletePartnerByPartnerName(partnerName);
        if (rowsDeleted == 1) {
            return new Response("success", LocalDateTime.now());
        } else {
            return new Response("failed", LocalDateTime.now());
        }
    }

    @DeleteMapping(value = "/rest/partnerVideo", produces = "application/json")
    public Response deletePartnerVideo(@RequestHeader String partnerId) {
        int rowsDeleted = partnerServiceImpl.deletePartnerVideoByPartnerId(Integer.parseInt(partnerId));
        if (rowsDeleted == 1) {
            return new Response("success", LocalDateTime.now());
        } else {
            return new Response("failed", LocalDateTime.now());
        }
    }

    @DeleteMapping(value = "/rest/partnerImage", produces = "application/json")
    public Response deletePartnerImage(@RequestHeader String partnerId) {
        int rowsDeleted = partnerServiceImpl.deletePartnerImageByPartnerId(Integer.parseInt(partnerId));
        if (rowsDeleted == 1) {
            return new Response("success", LocalDateTime.now());
        } else {
            return new Response("failed", LocalDateTime.now());
        }
    }
}