package com.fundzforus.server.controller.rest;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fundzforus.server.domain.NewsReader;
import com.fundzforus.server.domain.Response;
import com.fundzforus.server.exception.MissingMandatoryFieldsException;
import com.fundzforus.server.exception.NewsReaderAlreadyFoundException;
import com.fundzforus.server.exception.NewsReaderNotFoundException;
import com.fundzforus.server.service.INEWSReaderService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class NEWSReaderRestApiController {

    @Autowired
    private INEWSReaderService newReaderServiceImpl;

    @GetMapping(value = "/rest/allNEWS", produces = "application/json")
    public List<NewsReader> getAllNEWS() {
        return newReaderServiceImpl.findAllNEWS();
    }

    @PostMapping(value = "/rest/news", produces = "application/json", consumes = "application/json")
    public Response insertNEWS(@RequestBody NewsReader newsReader) {
        int rowsInserted = 0;
        try {
            rowsInserted = newReaderServiceImpl.createNews(newsReader);
        } catch (NewsReaderAlreadyFoundException | MissingMandatoryFieldsException e1) {
            return new Response("failed", LocalDateTime.now(), e1.getMessage());
        }
        if (rowsInserted == 1) {
            return new Response("success", LocalDateTime.now());
        } else {
            return new Response("failed", LocalDateTime.now());
        }
    }

    @PutMapping(value = "/rest/news", produces = "application/json")
    public Response updateNEWS(@RequestBody NewsReader newsReader) {
        int rowsUpdated = 0;
        try {
            rowsUpdated = newReaderServiceImpl.updateNews(newsReader);
        } catch (NewsReaderAlreadyFoundException | MissingMandatoryFieldsException e1) {
            return new Response("failed", LocalDateTime.now(), e1.getMessage());
        }
        if (rowsUpdated == 1) {
            return new Response("success", LocalDateTime.now());
        } else {
            return new Response("failed", LocalDateTime.now());
        }
    }

    @GetMapping(value = "/rest/news", produces = "application/json")
    public NewsReader getNewReader(@RequestHeader String id) {
    	NewsReader response = newReaderServiceImpl.getNEWSById(Integer.parseInt(id));
        return response;
    }

    @DeleteMapping(value = "/rest/news", produces = "application/json")
    public Response deleteNEWS(@RequestHeader String id) {
        int rowsDeleted = 0;
        try {
            rowsDeleted = newReaderServiceImpl.deleteNewsById((Integer.parseInt(id)));
        } catch (NewsReaderNotFoundException | MissingMandatoryFieldsException e1) {
            return new Response("failed", LocalDateTime.now(), e1.getMessage());
        }
        if (rowsDeleted == 1) {
            return new Response("success", LocalDateTime.now());
        } else {
            return new Response("failed", LocalDateTime.now());
        }
    }
}
