package com.fundzforus.server.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Data
public class Program {
    private int id;
    private String programName;
    private String programDescription;
    private String programDetails;
    private int bookings;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime programDateTime;
    private String programDateTimeField;
    private String programDateTimeStr;
    private String programDayOfMonth;
    private String programMonth;
    private String programTime;
    private String programImageUrl;
    private String programVideoUrl;
    private String programAddress;
    private String tenantId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime createDateTime;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime updateDateTime;
    private String createdBy;
    private String updatedBy;

    public String getProgramDayOfMonth() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");
        programDayOfMonth = programDateTime.format(formatter);
        return programDayOfMonth;
    }

    public String getProgramMonth() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM");
        programMonth = programDateTime.format(formatter);
        return programMonth;
    }

    public String getProgramTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        programTime = programDateTime.format(formatter);
        return programTime;
    }

    public String getProgramDateTimeStr() {
        if (programDateTime != null) {
            ZonedDateTime zdt = ZonedDateTime.of(programDateTime, ZoneId.of("UTC"));
            programDateTimeStr = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL).format(zdt);
        }

        return programDateTimeStr;
    }

    public String getProgramDateTimeField() {
        if (StringUtils.isBlank(programDateTimeField) && programDateTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yy HH:mm");
            programDateTimeField = programDateTime.format(formatter);
            return programDateTimeField;
        } else {
            return programDateTimeField;
        }
    }
}