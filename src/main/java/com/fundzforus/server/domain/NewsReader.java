package com.fundzforus.server.domain;
/**
 * CREATE TABLE `fundzforus`.`TBL_NEWS_READER` (
  `NEWS_ID` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `NEWS_TITLE` VARCHAR(200) NOT NULL,
  `NEWS_DESCRIPTION` VARCHAR(300) NOT NULL,
  `NEWSIMG_URL` VARCHAR(1024) NOT NULL,
  `NEWS_VIDEO_URL` VARCHAR(1024) NOT NULL, 
  `CREATE_DATETIME` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `CREATED_BY` VARCHAR(20) NOT NULL DEFAULT 'MOBILE_APP',
  `UPDATE_DATETIME` TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NULL,
  `UPDATED_BY` VARCHAR(20)  NULL DEFAULT 'MOBILE_APP'
  
)
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Data
public class NewsReader {
	private int id;
	private String title;
	private String category;
	private String description;
	private String imageURL;
	private String pdfURL;
	private String videoURL;
	private String publishedBy;
	private String createDateTimeStr;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	private LocalDateTime createDateTime;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	private LocalDateTime updateDateTime;
	private String createdBy;
	private String updatedBy;

	public String getCreateDateTimeStr() {
		if (createDateTime != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
			createDateTimeStr = createDateTime.format(formatter);
		}
		return createDateTimeStr;
	}
}