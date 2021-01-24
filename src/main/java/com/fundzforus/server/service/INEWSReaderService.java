package com.fundzforus.server.service;

import com.fundzforus.server.domain.NewsReader;
//import com.fundzforus.server.domain.UserVideo;

import java.util.List;

public interface INEWSReaderService {
	List<NewsReader> findAllNEWS();

	NewsReader getNEWSById(int id);

	int createNews(NewsReader newsReader);// insertNEWS

	int updateNews(NewsReader newsReader);

	int deleteNewsById(int id);
}